#include <stdint.h>
#include <stdio.h>
#include <stm32f407xx.h>
#include <string.h>
#include "ourCom.h"
#include "ourGpio.h"
#include "hx711.h"

void USART3_IRQHandler(void);
void USART6_IRQHandler(void);
void initSysTick(uint32_t ms);
void toGrams (int v);

char BUFFER[6]; //ENVIAR DATOS

int cont_temp=0;
char c[1];
volatile uint32_t temp_balioa;
volatile float peso, temp;

//BALANZA
volatile int read = -1;
volatile int value = -1;
volatile int media;

int main(void)
{
	HX711 sensor;
	HX711_init(&sensor);
	HX711_Tare(&sensor, 50);
	
	initGPIO();
	USART3_Init(9600);
	USART6_Init(9600);
	
 
	initSysTick(1000);
	enablePA0interruptOnExti0WhenRising();
	
	printMsgToUSART(USART3," ",1);
	printMsgToUSART(USART6," ",1);

	while(1)
	{
	//TEMP SENTSOREA
		// Hasi bihurketa
		ADC1->CR2 |= ADC_CR2_SWSTART;
		// Itxaron bihurketa
		while ((ADC1->SR & ADC_SR_EOC) == 0);
		// EOC zerora jarri berriz (ez da beharrezkoa)
		// ADC1->SR &=~ADC_SR_EOC;
		// Irakurri bihurketaren emaitza
		temp_balioa = ADC1->DR;
		// bihurtu boltaiara
		temp = (temp_balioa *330.0)/1023.0;
		
		
	//BALANZA
    read = HX711_Read(&sensor);
		value = sensor.offset - read;
    toGrams(value);	
	}
}

void toGrams (int v){
    peso =  (v*199)/47950;
}

void initSysTick(uint32_t ms){
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
		
		memset(BUFFER,'0',sizeof(BUFFER));
		cont_temp=0;
		sprintf(BUFFER,"%.2f", temp);
		
		printMsgToUSART(USART3,"t",1);
		printMsgToUSART(USART3,BUFFER,sizeof(BUFFER));
		printMsgToUSART(USART3,"\n",2);
		
		printMsgToUSART(USART6,"t",1);
		printMsgToUSART(USART6,BUFFER,sizeof(BUFFER));
		printMsgToUSART(USART6,"\n",2);
	}
}

void EXTI0_IRQHandler(void)
{
  EXTI->PR |= 0x01;
	memset(BUFFER,'0',sizeof(BUFFER));
	c[0]='p';
	
	sprintf(BUFFER,"%.4f", peso);
	
	printMsgToUSART(USART3,c,sizeof(c));
	printMsgToUSART(USART3,BUFFER,sizeof(BUFFER)); 
	printMsgToUSART(USART3,"\n",1); 
	
	printMsgToUSART(USART6,c,sizeof(c));
	printMsgToUSART(USART6,BUFFER,sizeof(BUFFER)); 
	printMsgToUSART(USART6,"\n",1); 
}

void USART3_IRQHandler(void)
{
		memset(BUFFER,'0',sizeof(BUFFER));
		char tmp = USART3->DR;
		
			if(tmp==116) //si es 't' 
			{
				c[0]='t';
				sprintf(BUFFER,"%.2f", temp);
				printMsgToUSART(USART3,c,sizeof(c));
				printMsgToUSART(USART3,BUFFER,sizeof(BUFFER));
				printMsgToUSART(USART3,"\n",1);
			}
			else if(tmp==112) //si es p
			{
				c[0]='p';
				sprintf(BUFFER,"%.2f", peso);
				printMsgToUSART(USART3,c,sizeof(c));
				printMsgToUSART(USART3,BUFFER,sizeof(BUFFER));
				printMsgToUSART(USART3,"\n",1);
			}
	}

void USART6_IRQHandler(void)
{	
	if(USART6->SR & USART_SR_RXNE)
	{
		memset(BUFFER,'0',sizeof(BUFFER));
		char tmp = USART6->DR;
		
			if(tmp==116) //si es 't' 
			{
				c[0]='t';
				sprintf(BUFFER,"%.2f", temp);
				printMsgToUSART(USART6,c,sizeof(c));
				printMsgToUSART(USART6,BUFFER,sizeof(BUFFER));
				printMsgToUSART(USART6,"\n",1);
			}
			else if(tmp==112) //si es p
			{
				c[0]='p';
				sprintf(BUFFER,"%.2f", peso);
				printMsgToUSART(USART6,c,sizeof(c));
				printMsgToUSART(USART6,BUFFER,sizeof(BUFFER));
				printMsgToUSART(USART6,"\n",1);
			}
	}
}

