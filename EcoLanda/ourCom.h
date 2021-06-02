#ifndef OUR_COM_H
#define OUR_COM_H

//#define NVIC_ISER1 (*(uint32_t*) (0xE000E100 + 0x04))
//#define NVIC_ISER2 (*(uint32_t*) (0xE000E100 + 0x08))

#define AF_SIZE 4 /**< Size of each pin inside the Alternate Function register. */
#define ALTERNATE_USART3 7 /**< Alternate function corresponding to the USART3. */
#define ALTERNATE_USART6 8 /**< Alternate function corresponding to the USART6. */
#define AF_USART3_TX 2 /**< The position of the USART3 TX pin inside the Alternate Function register. */
#define AF_USART3_RX 3 /**< The position of the USART3 RX pin inside the Alternate Function register. */
#define AF_USART3_RS232_TX 0 /**< The position of the USART3 TX pin for RS232 port inside the Alternate Function register. */
#define AF_USART3_RS232_RX 1 /**< The position of the USART3 RX pin for RS232 port inside the Alternate Function register. */
#define AF_USART6_TX 6 /**< The position of the USART6 TX pin inside the Alternate Function register. */
#define AF_USART6_RX 1 /**< The position of the USART6 RX pin inside the Alternate Function register. */

#define MODER_SIZE 2 /**< Size of each pin inside of the MODER register of the GPIO. */
#define GPIO_MODER_ALTERNATE 2 /**< Set the pin in Alternate function mode in the MODER register. */
#define USART6_TX_PIN 6 /**< PIN of the USART6 TX on the GPIO. */ 
#define USART6_RX_PIN 9 /**< PIN of the USART6 RX on the GPIO. */
#define USART3_RX_PIN 11 /**< PIN of the USART3 TX on the GPIO. */
#define USART3_TX_PIN 10 /**< PIN of the USART3 RX on the GPIO. */
#define USART3_RS232_TX_PIN 8 /**< PIN of the USART3 TX for the RS232 port on the GPIO. */
#define USART3_RS232_RX_PIN 9 /**< PIN of the USART3 RX for the RS232 port on the GPIO. */

typedef enum E_COM {COM1,COM2} COM;

void initUSART(COM com);
void printMsgToUSART(USART_TypeDef *usart,char *msg, int length);


void USART3_Init(uint16_t speed);
void USART6_Init(uint16_t speed);
void USART_Enable_RCC(USART_TypeDef *usart);
void enableGPIO_RCC(GPIO_TypeDef *gpio);


void USART_ConfGPIO(USART_TypeDef *usart);

void USART_Conf(USART_TypeDef *usart, uint16_t speed);
void USART_ConfIRQ_RX(USART_TypeDef *usart);
	
#endif
