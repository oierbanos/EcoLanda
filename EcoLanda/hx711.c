#include "hx711.h"
#include "ourGpio.h"

void HX711_init (HX711* sensor){
	sensor->PD_SCK_PinType=GPIOF;
	sensor->PD_SCK_PinNumber=GPIO_PIN_9;
	sensor->DOUT_PinType=GPIOF;
	sensor->DOUT_PinNumber=GPIO_PIN_8;
	sensor->mode=2;
}

int HX711_Read(HX711* H) {	
	int count = 0;
	_Bool isNegative = 0;
	GPIO_WritePin(H->PD_SCK_PinType, H->PD_SCK_PinNumber, GPIO_PIN_RESET);
	while (GPIO_ReadPin(H->DOUT_PinType, H->DOUT_PinNumber)) {
		
	}
	for (int i = 0; i < 24; i++) {
		GPIO_WritePin(H->PD_SCK_PinType, H->PD_SCK_PinNumber, GPIO_PIN_SET);
		count = count << 1;
		GPIO_WritePin(H->PD_SCK_PinType, H->PD_SCK_PinNumber,
				GPIO_PIN_RESET);
		if (GPIO_ReadPin(H->DOUT_PinType, H->DOUT_PinNumber)) {
			if (i == 0) {
				isNegative = 1;
			}
			count++;
		}
	}
	if (isNegative) {
		count = count ^ 0xFF000000;
		//count = count ^ 0x800000;
	}
	GPIO_WritePin(H->PD_SCK_PinType, H->PD_SCK_PinNumber, GPIO_PIN_SET);
	GPIO_WritePin(H->PD_SCK_PinType, H->PD_SCK_PinNumber, GPIO_PIN_RESET);
	for (int i = 0; i < H->mode; i++) {
		GPIO_WritePin(H->PD_SCK_PinType, H->PD_SCK_PinNumber, GPIO_PIN_SET);
		GPIO_WritePin(H->PD_SCK_PinType, H->PD_SCK_PinNumber,
				GPIO_PIN_RESET);
	}
	return count;
	
}

int HX711_AvgRead(HX711* H, int times) {
	int64_t sum = 0;
	for (int i = 0; i < times; i++) {
		sum += HX711_Read(H);
	}
	return sum / times;
}

void HX711_Tare(HX711* H, int times) {
	H->offset = HX711_AvgRead(H, times);
}

int HX711_GetValue(HX711* H) {
	return (H->offset) - HX711_Read(H);
}

int HX711_GetAvgValue(HX711* H, int times) {
	return HX711_AvgRead(H, times) - (H->offset);
}
