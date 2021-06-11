#include "hx711.h"
#include "ourGpio.h"

/*
* Function: HX711_Init
* ---------------------
* Configura la estructura del sensor para asignar los pines del microcontrolador que se van
* a utilizar.
* 
* @param sensor - Una estructura con la configuración de los pines del microcontrolador que se utilizan
* 			 	  para comunicarse con el ADC HX711.
*/

void HX711_init (HX711* sensor){
	sensor->PD_SCK_PinType=GPIOF;
	sensor->PD_SCK_PinNumber=GPIO_PIN_9;
	sensor->DOUT_PinType=GPIOF;
	sensor->DOUT_PinNumber=GPIO_PIN_8;
	sensor->mode=2;
}
/*
* Function: HX711_Read
* ---------------------
* Devuelve el valor que mide la balanza en ese momento utilizando el ADC HX711.
*
* 
* @param H - Una estructura con la configuración de los pines del microcontrolador que se utilizan
* 			 para comunicarse con el ADC HX711.
* @return Valor que mide el sensor
*/
int HX711_Read(HX711* H) {	
	int count = 0;
	_Bool isNegative = 0;
	/*  Cuando los datos del ADC no están listos para ser leidos, el pin DOUT está activo.
		El pin SCK (clock serial) deberia de estar desactivado.	*/

	GPIO_WritePin(H->PD_SCK_PinType, H->PD_SCK_PinNumber, GPIO_PIN_RESET);
	//Cuando el pin DOUT está a 1, significa que el ADC NO está listo para enviar los datos.
	//Por lo tanto, esperamos a que se ponga a 0 para recibir los datos.
	while (GPIO_ReadPin(H->DOUT_PinType, H->DOUT_PinNumber)) {
		
	}
	//Cuando se envian pulsos por SCK, los datos se shiftean del pin DOUT.
	//Cada pulso shiftea un bit, empezando por el MSB (el de la izquierda).
	for (int i = 0; i < 24; i++) {
		//Se envia un pulso
		GPIO_WritePin(H->PD_SCK_PinType, H->PD_SCK_PinNumber, GPIO_PIN_SET);
		//Se shiftea el numero
		count = count << 1;
		//Se pone el pin a 0 para terminar de mandar el pulso.
		GPIO_WritePin(H->PD_SCK_PinType, H->PD_SCK_PinNumber,
				GPIO_PIN_RESET);
		//Se lee el valor mandado por el ADC		
		if (GPIO_ReadPin(H->DOUT_PinType, H->DOUT_PinNumber)) {
			if (i == 0) {
				isNegative = 1;
			}
			//Se suma un 1 (se pone el ultimo bit a 1) solamente si el valor recibido es un 1
			count++;
		}
	}
	if (isNegative) {
		count = count ^ 0xFF000000;
		//count = count ^ 0x800000;
	}
	//El pulso 25 termina la comunicación y hace que el pin DOUT se ponga a 1.
	GPIO_WritePin(H->PD_SCK_PinType, H->PD_SCK_PinNumber, GPIO_PIN_SET);
	GPIO_WritePin(H->PD_SCK_PinType, H->PD_SCK_PinNumber, GPIO_PIN_RESET);
	/*Segun la configuración elegida, se mandan hasta 27 pulsos para cambiar la ganancia
	  o el canal de entrada*/
	for (int i = 0; i < H->mode; i++) {
		GPIO_WritePin(H->PD_SCK_PinType, H->PD_SCK_PinNumber, GPIO_PIN_SET);
		GPIO_WritePin(H->PD_SCK_PinType, H->PD_SCK_PinNumber,
				GPIO_PIN_RESET);
	}
	return count;
	
}
/*
* Function: HX711_AvgRead
* ---------------------
* Devuelve la media de los c valores que mide la balanza en ese momento utilizando el ADC HX711.
*
* 
* @param H - Una estructura con la configuración de los pines del microcontrolador que se utilizan
* 			 para comunicarse con el ADC HX711.
* @param times - Veces que se van a pedir valores
* @return Media de los valores que lee el sensor
*/
int HX711_AvgRead(HX711* H, int times) {
	int64_t sum = 0;
	for (int i = 0; i < times; i++) {
		sum += HX711_Read(H);
	}
	return sum / times;
}
/*
* Function: HX711_Tare
* ---------------------
* Cambia el offset teniendo en cuenta el valor que lee el sensor para hacer una tara.
*
* 
* @param H - Una estructura con la configuración de los pines del microcontrolador que se utilizan
* 			 para comunicarse con el ADC HX711.
* @param times - Veces que se van a pedir valores
*/
void HX711_Tare(HX711* H, int times) {
	H->offset = HX711_AvgRead(H, times);
}
/*
* Function: HX711_GetValue
* ---------------------
* Devuelve el valor del sensor teniendo en cuenta el offset.
*
* 
* @param H - Una estructura con la configuración de los pines del microcontrolador que se utilizan
* 			 para comunicarse con el ADC HX711.
* @return Resta entre el offset y el valor del sensor.
*/

int HX711_GetValue(HX711* H) {
	return (H->offset) - HX711_Read(H);
}
/*
* Function: HX711_GetAvgValue
* ---------------------
* Devuelve la media de los c valores que mide la balanza en ese momento teniendo en cuenta el offset.
*
* 
* @param H - Una estructura con la configuración de los pines del microcontrolador que se utilizan
* 			 para comunicarse con el ADC HX711.
* @param times - Veces que se van a pedir valores
* @return Media de los valores que lee el sensor teniendo en cuenta el offset
*/

int HX711_GetAvgValue(HX711* H, int times) {
	return  (H->offset) - HX711_AvgRead(H, times);
}
