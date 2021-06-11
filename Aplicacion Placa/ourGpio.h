#ifndef OUR_GPIO_H
#define	OUR_GPIO_H

#include <stdint.h>
#include <stm32f407xx.h>

#define GPIO_PIN_9 (uint32_t) 9
#define GPIO_PIN_8 (uint32_t) 8

typedef enum
{
  GPIO_PIN_RESET = 0,
  GPIO_PIN_SET = 1
}GPIO_PinState;


typedef enum
{ 
  GPIO_Mode_IN   = 0x00, /*!< GPIO Input Mode */
  GPIO_Mode_OUT  = 0x01, /*!< GPIO Output Mode */
  GPIO_Mode_AF   = 0x02, /*!< GPIO Alternate function Mode */
  GPIO_Mode_AN   = 0x03  /*!< GPIO Analog Mode */
}GPIOMode_Type;

void initGPIO(void);
void initGpioPinMode(GPIO_TypeDef *, uint32_t pin, GPIOMode_Type mode);
void enablePA0interrupt(void);
void GPIO_WritePin(GPIO_TypeDef* gpiox, uint32_t pinN, uint32_t state);

GPIO_PinState getGpioPinValue(GPIO_TypeDef * gpio, uint16_t pin);
uint32_t GPIO_ReadPin(GPIO_TypeDef * gpio, uint32_t pin);
#endif
