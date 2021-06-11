#include <stm32f407xx.h>
#include "ourCom.h"

void USART6_Init(uint16_t speed) {
	enableGPIO_RCC(GPIOC);
	enableGPIO_RCC(GPIOG);
	USART6_Enable_RCC();
	USART6_ConfGPIO();
	USART_Conf(USART6, speed);
	USART6_ConfIRQ_RX();
}

void USART6_Enable_RCC(void) {
		RCC->APB2ENR |= RCC_APB2ENR_USART6EN;	
}

/******************************************//**
*Enables the GPIOx clock on the RCC device.
*@param gpio The GPIO to enable.
*
********************************************/
void enableGPIO_RCC(GPIO_TypeDef *gpio) {
	if(gpio == GPIOA) {
		RCC->AHB1ENR |= RCC_AHB1ENR_GPIOAEN;
	} else if(gpio == GPIOB) {
		RCC->AHB1ENR |= RCC_AHB1ENR_GPIOBEN;
	} else if(gpio == GPIOC) {
		RCC->AHB1ENR |= RCC_AHB1ENR_GPIOCEN;
	} else if(gpio == GPIOD) {
		RCC->AHB1ENR |= RCC_AHB1ENR_GPIODEN;
	} else if(gpio == GPIOE) {
		RCC->AHB1ENR |= RCC_AHB1ENR_GPIOEEN;
	} else if(gpio == GPIOF) {
		RCC->AHB1ENR |= RCC_AHB1ENR_GPIOFEN;
	} else if(gpio == GPIOG) {
		RCC->AHB1ENR |= RCC_AHB1ENR_GPIOGEN;
	} else if(gpio == GPIOH) {
		RCC->AHB1ENR |= RCC_AHB1ENR_GPIOHEN;
	} else if(gpio == GPIOI) {
		RCC->AHB1ENR |= RCC_AHB1ENR_GPIOIEN;
	}
}

/******************************************//**
*Configures the USART on the GPIO. Configures the USART on the GPIO ports, 
*setting the outputs and their mode. 
* - USART3 Port and Pins:
*	+ TX: PC10 - UEXT Connector Pin 9
*	+ RX: PC11 - UEXT Connector Pin 7
* - USART6 Port and Pins:
*	+ TX: PC6 - UEXT Connector Pin 3
*	+ RX: PG9 - UEXT Connector Pin 4
********************************************/
void USART6_ConfGPIO(void) {
		GPIOC->MODER |= (GPIO_MODER_ALTERNATE << (USART6_TX_PIN * MODER_SIZE));
		GPIOG->MODER |= (GPIO_MODER_ALTERNATE << (USART6_RX_PIN * MODER_SIZE));
		GPIOC->AFR[0] |= (ALTERNATE_USART6 << (AF_USART6_TX * AF_SIZE));
		GPIOG->AFR[1] |= (ALTERNATE_USART6 << (AF_USART6_RX * AF_SIZE));
}

/*
* Function: USART_Conf
* ---------------------
* Se configura la velocidad de transmision.
*
* Configures USART3 or 6 with the desired speed, 8 data bits, 1 stop bit, no parity and in TX and RX mode.
*/
void USART_Conf(USART_TypeDef *usart, uint16_t speed) {
	switch(speed) {
		case 1200: usart->BRR = 0x3415; break; //1200 bps @PCLK 16Mhz
		case 4800: usart->BRR = 0x222E; break; //4800 bps @PCLK 16Mhz
		case 9600: usart->BRR = 0x683; break;  //9600 bps @PCLK 16Mhz
		case 38400: usart->BRR = 0x1A1; break; //36400 bps @PCLK 16Mhz
	}
	usart->CR1 = USART_CR1_UE | USART_CR1_TE | USART_CR1_RE;
}


/*
* Function: USART6_ConfIRQ_RX
* ---------------------
* Se configura USART para que genere interrupciones en eventos RX.
*
*/
void USART6_ConfIRQ_RX(void) {
		USART6->CR1 |= USART_CR1_RXNEIE;
		NVIC_EnableIRQ(USART6_IRQn);
}

/*
* Function: printMsgToUSART6
* ---------------------
* Se envia el mensaje a traves de serial
*
* Se almacena el primer valor del mensaje en DR (Data Register) y cuando TXE se pone a 1 se puede enviar.
* Se va shifteando el valor a enviar hasta que se llega al final del mensaje, es decir, hasta que el contador 
* sea igual a la longitud del mesnaje.
*
*/
void printMsgToUSART6(char *msg, int length)
{
	int i=0;
	do
	{
		USART6->DR = (uint32_t) *msg; // Almacenar el primer valor del mensaje 
		while(!(USART6->SR & USART_SR_TC)); // Esperar a que TXE=1, y se transfiere el mensaje
		msg++;
		i++;
	}while(i<length);
}

