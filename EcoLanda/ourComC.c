#include <stm32f407xx.h>
#include "ourCom.h"


void USART3_Init(uint16_t speed) {
	enableGPIO_RCC(GPIOC);
	USART_Enable_RCC(USART3);
	USART_ConfGPIO(USART3);
	USART_Conf(USART3, speed);
	USART_ConfIRQ_RX(USART3);
}


void USART6_Init(uint16_t speed) {
	enableGPIO_RCC(GPIOC);
	enableGPIO_RCC(GPIOG);
	USART_Enable_RCC(USART6);
	USART_ConfGPIO(USART6);
	USART_Conf(USART6, speed);
	USART_ConfIRQ_RX(USART6);
}

void USART_Enable_RCC(USART_TypeDef *usart) {
	
	if(usart == USART6) {
		RCC->APB2ENR |= RCC_APB2ENR_USART6EN;
	}else if(usart == USART3) {
		RCC->APB1ENR |= RCC_APB1ENR_USART3EN;	
	}
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
void USART_ConfGPIO(USART_TypeDef *usart) {
	if(usart == USART3) {
		GPIOC->MODER |= (GPIO_MODER_ALTERNATE << (USART3_TX_PIN * MODER_SIZE));
		GPIOC->MODER |= (GPIO_MODER_ALTERNATE << (USART3_RX_PIN * MODER_SIZE));
		GPIOC->AFR[1] |= (ALTERNATE_USART3 << (AF_USART3_TX * AF_SIZE));
		GPIOC->AFR[1] |= (ALTERNATE_USART3 << (AF_USART3_RX * AF_SIZE));
	}else if(usart == USART6) {
		GPIOC->MODER |= (GPIO_MODER_ALTERNATE << (USART6_TX_PIN * MODER_SIZE));
		GPIOG->MODER |= (GPIO_MODER_ALTERNATE << (USART6_RX_PIN * MODER_SIZE));
		GPIOC->AFR[0] |= (ALTERNATE_USART6 << (AF_USART6_TX * AF_SIZE));
		GPIOG->AFR[1] |= (ALTERNATE_USART6 << (AF_USART6_RX * AF_SIZE));
	}
}

//Configures USART3 or 6 with the desired speed, 8 data bits, 1 stop bit, no parity and in TX and RX mode.
void USART_Conf(USART_TypeDef *usart, uint16_t speed) {
	switch(speed) {
		case 1200: usart->BRR = 0x3415; break; //1200 bps @PCLK 16Mhz
		case 4800: usart->BRR = 0x222E; break; //4800 bps @PCLK 16Mhz
		case 9600: usart->BRR = 0x683; break;  //9600 bps @PCLK 16Mhz
		case 38400: usart->BRR = 0x1A1; break; //36400 bps @PCLK 16Mhz
	}
	usart->CR1 = USART_CR1_UE | USART_CR1_TE | USART_CR1_RE;
}

//*Configure usart to generate Interrupts on RX events.
void USART_ConfIRQ_RX(USART_TypeDef *usart) {
	if(usart == USART3) {
		USART3->CR1 |= USART_CR1_RXNEIE;
		NVIC_EnableIRQ(USART3_IRQn);
	} else if(usart == USART6) {
		USART6->CR1 |= USART_CR1_RXNEIE;
		NVIC_EnableIRQ(USART6_IRQn);
	}
}

void printMsgToUSART(USART_TypeDef *usart,char *msg, int length)
{
	int i=0;
	do
	{
		USART6->DR = (uint32_t) *msg;
		while(!(usart->SR & USART_SR_TC)); //wait for TXE=1, data transferred
		msg++;
		i++;
	}while(i<length);
}
