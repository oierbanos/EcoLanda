#include <stdint.h>
#include <stdio.h>
#include <stm32f407xx.h>
#include <string.h>
#include "ourCom.h"
#include "ourGpio.h"
#include "hx711.h"

#define MAX_BUFFER 6

void initSysTick(uint32_t ms);
void enviarPeso (HX711 sensor);
void enviarTemperatura (void);
float toGrams (int v);

// ENVIAR DATOS
char BUFFER[MAX_BUFFER];
char c[1];

// CONTADOR SYSTICK
int cont_temp=0;

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
	printMsgToUSART6(" ", 1);

	while(1) {
		if (solicitudPeso) {
			// Si se ha solicitado el peso, enviarlo mediante serial.
			enviarPeso(sensor);
		}
		if (solicitudTemperatura) {
			// Si se ha solicitado la temperatura enviarla mediante serial.
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
	
	c[0]='t';	// Enviar una 't' para especificar que se va a enviar temperatura.
	sprintf(BUFFER,"%.2f", temp);	// Añadir el valor de la temperatura al buffer.
	
	printMsgToUSART6(c, sizeof(c));							// Enviar 't'.
	printMsgToUSART6(BUFFER, sizeof(BUFFER));		// Enviar valor temperatura.
	printMsgToUSART6("\n",1);										// Enviar '\n' -> Fin de mensaje.

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
	
	c[0]='p';	// Enviar una 'p' para especificar que se va a enviar peso.
	sprintf(BUFFER,"%.2f", peso);	// Añadir el valor de la temperatura al buffer.

	printMsgToUSART6(c,sizeof(c));
	printMsgToUSART6(BUFFER,sizeof(BUFFER)); 
	printMsgToUSART6("\n",1); 
	
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
    return ((v*199)/47950)-271;
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
    SysTick->LOAD  = 16000000U/1000*ms - 1U; // kontagailuak 0 tik hemen definitutako baliora kontatuko du 16Mhz/1000 mseg
	  SysTick->VAL   = 0U; //garbitu current value
    SysTick->CTRL  |= 0x00000003; // tickint y enable
  }
}

void SysTick_Handler(void) 
{
	if(cont_temp<900) cont_temp++; 
	if(cont_temp==899){ 
		solicitudTemperatura = 1;		
	}
}

void EXTI0_IRQHandler(void)
{
  EXTI->PR |= 0x01;
	solicitudPeso = 1;
}

void USART6_IRQHandler(void)
{	
	if(USART6->SR & USART_SR_RXNE)
	{
		char tmp = USART6->DR;
		if(tmp==116) //si es 't' 
		{
			solicitudTemperatura = 1;
			
		}
		else if(tmp==112) //si es p
		{
			solicitudPeso = 1;
		}
	}
}

