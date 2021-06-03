#include <stm32f407xx.h>
#include "ourGpio.h"
#include "ourCom.h"

void initGPIO(void)
{
	//#####################  TEMPERATURA Sents. Konf #####################
	RCC->AHB1ENR |= RCC_AHB1ENR_GPIOBEN;
	initGpioPinMode(GPIOB, 0, GPIO_Mode_AN); //GPIOB como entrada analagica
	
	//Enciende ADC
	RCC->APB2ENR |= RCC_APB2ENR_ADC1EN;
	
	//Piztu AD bihurgailuaren zirkuitua
	ADC1->CR1 |= ADC_CR1_RES_0;
	ADC1->CR1 &=~ ADC_CR1_RES_1;
	ADC1->CR1 &=~ ADC_CR1_SCAN;
	ADC1->CR2 |= ADC_CR2_ADON;
	
	// Ezarri bihurketa abiadura (15 ziklo, IN8-entzat)
	ADC1->SMPR2 &=~(7<<24);	// SMP10[2:0] = 000
	ADC1->SMPR2 |= (1<<24);  // SMP10[2:0] = 001
	
	// Aukeratu bihurtu nahi dugun sarrera
	ADC1->SQR1 &=~(0xF<<20);	// L[3:0] = 0000
	ADC1->SQR3 |= (8<<0);		// SQ1[4:0] = 8
	
	//##################### BALANZA HX711 #######################
	
	RCC->AHB1ENR |= RCC_AHB1ENR_GPIOFEN;
	//Configuramos los puertos
	GPIO_WritePin(GPIOF, 9, GPIO_PIN_RESET);
   //GPIOF pin 8 -> Pin DT (DOUT) -> Recibe datos -> INPUT
   initGpioPinMode(GPIOF, 8, GPIO_Mode_IN);		
   //GPIOF pin 9 -> Es el pin SCK (el clock que se le manda al HX711) -> OUTPUT	
	 initGpioPinMode(GPIOF, 9, GPIO_Mode_OUT);
}


void initGpioPinMode(GPIO_TypeDef* gpio, uint32_t pin, GPIOMode_Type mode)
{
 gpio->MODER &= ~(3<< (pin*2)); 
 gpio->MODER |= mode << (pin*2);
}

void GPIO_WritePin(GPIO_TypeDef* gpiox, uint32_t pinN, uint32_t state)
{	
	if(!state) 
	{
		gpiox->BSRR = (1 << (pinN + 16));
	}	else {
		gpiox->BSRR = (1 << pinN);
	}

}

uint32_t GPIO_ReadPin(GPIO_TypeDef * gpio, uint32_t pin)
{
	return (gpio->IDR & (0x01<<pin));
}

void enablePA0interrupt(void)
{
     //SYSCFG gaitu
	uint32_t *APB2ENR=(uint32_t*)(0x40023800+0x44);
	*APB2ENR |= (1<<14);
	//RCC->APB2ENR |= RCC_SYSYSCFG_EN_BIT ;
	  //sysConfig configuratu (defektuzko balioen gauz bera idaztera goaz). 0 jarrita
	SYSCFG->EXTICR[0] &=~(0x0F);
  
  //exti konfiguratu
	uint32_t *extiRTSR=(uint32_t*)(0x40013C00+0x08);
	*extiRTSR |= 0x01;
	uint32_t *extiFTSR=(uint32_t*)(0x40013C00+0x0C);
	*extiFTSR &= ~0x01;
	uint32_t *extiIMR=(uint32_t*)(0x40013C00);
  *extiIMR |= 0x01;
  //NVIC konfiguratu
	uint32_t *nvic=(uint32_t*)(0xE000E100);
	*nvic |=0x01<<6;
}

