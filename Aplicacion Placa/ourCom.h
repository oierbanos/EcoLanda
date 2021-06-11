#ifndef OUR_COM_H
#define OUR_COM_H


#define AF_SIZE 4 /**< Size of each pin inside the Alternate Function register. */
#define ALTERNATE_USART6 8 /**< Alternate function corresponding to the USART6. */
#define AF_USART6_TX 6 /**< The position of the USART6 TX pin inside the Alternate Function register. */
#define AF_USART6_RX 1 /**< The position of the USART6 RX pin inside the Alternate Function register. */

#define MODER_SIZE 2 /**< Size of each pin inside of the MODER register of the GPIO. */
#define GPIO_MODER_ALTERNATE 2 /**< Set the pin in Alternate function mode in the MODER register. */
#define USART6_TX_PIN 6 /**< PIN of the USART6 TX on the GPIO. */ 
#define USART6_RX_PIN 9 /**< PIN of the USART6 RX on the GPIO. */


void USART6_Init(uint16_t speed);
void printMsgToUSART6(char *msg, int length);


void USART6_Enable_RCC(void);
void enableGPIO_RCC(GPIO_TypeDef *gpio);


void USART6_ConfGPIO(void);

void USART_Conf(USART_TypeDef *usart, uint16_t speed);
void USART6_ConfIRQ_RX(void);
	
#endif
