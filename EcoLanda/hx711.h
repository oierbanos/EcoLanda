#ifndef HX711_H_
#define HX711_H_

#include <stm32f407xx.h>
#include <stdint.h>

typedef struct {
	GPIO_TypeDef* PD_SCK_PinType;	//GPIOx
	uint16_t PD_SCK_PinNumber;		//GPIO_Pin
	GPIO_TypeDef* DOUT_PinType;		//GPIOx
	uint16_t DOUT_PinNumber;		//GPIO_Pin
	int offset;
	int mode;		// 0 Input channel A, gain=128
					// 1 Input channel B, gain=32
					// 2 Input channel A, gain=64
} HX711;

void HX711_init (HX711* sensor);
int HX711_Read(HX711* H);
int HX711_AvgRead(HX711* H, int times);
void HX711_Tare(HX711* H, int times);
int HX711_GetValue(HX711* H);
int HX711_GetAvgValue(HX711* H, int times);

#endif /* HX711_H_ */
