#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <stm32f407xx.h>
#include <string.h>
#include "ourCom.h"
#include "ourGpio.h"
#include "hx711.h"

#define MAX_BUFFER 9

void initSysTick(uint32_t ms);
void enviarPeso (HX711 sensor);
void enviarTemperatura (void);
float toGrams (int v);

// ENVIAR DATOS
char BUFFER[MAX_BUFFER];
// CONTADOR SYSTICK
int contTemp = 0;
// DATOS SENSORES
volatile float peso, temp;

// BALANZA
volatile int read = -1;
volatile int value = -1;

// GESTION EVENTOS
int solicitudPeso = 0;
int solicitudTemperatura = 0;

int main(void)
{
	HX711 sensor;
	HX711_init(&sensor);				// Inicializar sensor de peso HX711.
	HX711_Tare(&sensor, 50);		// Calibrar el sensor.
	
	initGPIO();						// Inicializar GPIO.
	USART6_Init(9600);		// Inicializar USART6.
 
	initSysTick(1000);		// Inicializar SysTick.
	enablePA0interrupt();	// Permitir realizar excepciones mediante PA0.
	
	// Enviar un mensaje en blanco. Evita que TeraTerm borre el primer caracter enviado.
	// En el caso de utilizar TeraTerm descomentar la linea de debajo.
	// printMsgToUSART6(" ", 1);

	while(1) {
		if (solicitudPeso) {
			// Si se ha solicitado el peso en USART6_IRQHandler, enviarlo mediante serial.
			enviarPeso(sensor);
		}
		if (solicitudTemperatura) {
			// Si se ha solicitado la temperatura en USART6_IRQHandler, enviarla mediante serial.
			enviarTemperatura();
		}
	}
}

/*
* Function: enviarTemperatura
* ---------------------------
* Recoge el valor que está recogiendo el ADC conectado al sensor de temperatura y lo
* transmite mediante cable serial haciendo uso del USART6.
*
* Formato de envio: 't' + valor_temperatura + '\n'
*/

void enviarTemperatura (void){
	// SENSOR DE TEMPERATURA
	// Comenzar conversion en el ADC.
	ADC1->CR2 |= ADC_CR2_SWSTART;
	
	// Esperar a que termine la conversion.
	while ((ADC1->SR & ADC_SR_EOC) == 0);

	// Leer valor de la conversion y convertirlo a temperatura.
	uint32_t temp_balioa = ADC1->DR;
	temp = (temp_balioa *330.0)/1023.0;
	// Limpiar el buffer (poner todo a 0).
	memset(BUFFER,'0',sizeof(BUFFER));
	sprintf(BUFFER,"t%.2f\n", temp);	// Añadir al buffer la cabecera 't' el balor y \n.

	printMsgToUSART6(BUFFER, strlen(BUFFER));		// Enviar valor temperatura.

	// Indicar que la solicitud ha sido cubierta.
	solicitudTemperatura = 0;
}

/*
* Function: enviarPeso
* --------------------
* Recoger el valor que esta midiendo la bascula en ese preciso momento y
* lo transmite mediante el uso de cable serial, via USART6.
*
* sensor: HX711 type struct, contiene los elementos del sensor de peso.
*/
void enviarPeso (HX711 sensor){
	//BALANZA
	// Leer el valor que recoge el sensor.
	read = HX711_Read(&sensor);
	// Cambiar el valor teniendo en cuenta la desviacion del sensor.
	value = sensor.offset - read;
	// Transformar el valor a gramos.
	peso = toGrams(value);

	// Limpiar el buffer
	memset(BUFFER,'0',sizeof(BUFFER));
	sprintf(BUFFER,"p%.0f\n", peso);	// Añadir al buffer la cabecera 't' el balor y \n.
	
	printMsgToUSART6(BUFFER,strlen(BUFFER)); 
	
	solicitudPeso = 0;
}

/*
* Function: toGrams
* -----------------
* Transformar un valor de voltage a gramos.
*
* v: Valor de voltage recogido por el sensor.
*
* return: Valor del voltage transformado a gramos.
*/
float toGrams (int v) {
    return ((v*199)/47950)-270;
}

/*
* Function: initSysTick
* ---------------------
* Inicializar el SysTick para que cuente hasta una cantidad de segundos.
*
* ms: Cantidad de milisegundos que tiene que contar SisTick hasta enviar una señal.
*/
void initSysTick(uint32_t ms) {
  if(ms)
  {
    SysTick->LOAD  = 16000000U/1000*ms - 1U; // El contador, contara desde 0 hasta el valor definido 16Mhz/1000 mseg
	  SysTick->VAL   = 0U; // Limpiar valor actual
    SysTick->CTRL  |= 0x00000003; // Activar tickint y enable 
  }
}

/*
* Function: SysTick_Handler
* ---------------------
* Configurar la interrupción que produce el systick.
*
* Se produce la excepcion cada 8s y como se desea enviar la temperatura cada 2 horas (7200s), se cuenta mediante la varibale
* contTemp 900 veces, puesto que 8*900=7200. Cuando el contador sea 899, solicitudTemperatura se pone a 1 y se resetea el contador.
* 
*/
void SysTick_Handler(void) 
{
	if(contTemp < 900) contTemp++;
	if(contTemp == 899){ 
		solicitudTemperatura = 1;		
		contTemp = 0;
	}
}

/*
* Function: EXTI0_IRQHandler
* -----------------
* Se configura la excepcion que produce EXTI0 al pulsar WKUP .
*
* Se pone el PR (Pending register) a 1 para que salga de la interrupcion y no se quede en un bucle.
* solicitudPeso se pone a 1, y a continuacion en el while del main se comprueba y entra en la funcion para  
* devolver el peso mediante enviarPeso.
* 
*/
void EXTI0_IRQHandler(void)
{
  EXTI->PR |= 0x01;
	solicitudPeso = 1;
}

/*
* Function: USART6_IRQHandler
* ---------------------
* Al recibir un valor mediante serial por el puerto USART6, entra en esta función.
*
* Si ese valor es una t, se cambia el valor de solicitudTemperatura a 1, y a continuacion se procede 
* a devolver la temperatura mediante la funcion enviarTemperatura. Lo mismo pero con el peso si el valor es una p. 
* si es distinto, no hace nada.
*/
void USART6_IRQHandler(void)
{	
	if(USART6->SR & USART_SR_RXNE) //Status Register SR: (TXE, RXNE, CTS, PE...)  # RXNE: El bit se pone a 1 cuando los datos han llegado al registro DR y se pueden leer. (Read data register not empty)
	{
		char tmp = USART6->DR; //Se guarda en la variable tmp el valor recibido que se encuentra en DR (Data Register[8:0] bits)
		if(tmp == 116) //si es 't' 
		{
			solicitudTemperatura = 1;
			
		}
		else if(tmp==112) //si es 'p'
		{
			solicitudPeso = 1;
		}
	}
}

