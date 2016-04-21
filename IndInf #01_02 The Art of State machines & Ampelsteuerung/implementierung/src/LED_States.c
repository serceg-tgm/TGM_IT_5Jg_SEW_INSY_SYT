/**
  ******************************************************************************
  * @file		LED_States.c
  * @author  	Stefan Erceg
  * @version 	20150928
  * @brief		Hier werden die verschiedenen Zustaende der LEDs verwaltet.
  *
  * In diesem File existieren Funktionen, die fuer das Leuchten der spezifischen LEDs zustaendig sind. Zu Beginn
  * jeder Set-Funktion werden alle LEDs reseted.
  ******************************************************************************
*/

#include "stm32f3xx.h"
#include "stm32f3_discovery.h"

#include "LED_States.h"

void resetAllLEDs() {
	BSP_LED_Off(LED_RED);
	BSP_LED_Off(LED_ORANGE);
	BSP_LED_Off(LED_GREEN_2);
}

void setRedLED() {
	resetAllLEDs();
	BSP_LED_On(LED_RED);
	HAL_Delay(2000);
}

void setRedYellowLEDs() {
	resetAllLEDs();
	BSP_LED_On(LED_RED);
	BSP_LED_On(LED_ORANGE);
	HAL_Delay(2000);
}

void setGreenLED() {
	resetAllLEDs();
	BSP_LED_On(LED_GREEN_2);
	HAL_Delay(2000);
}

void setBlinkingGreenLED() {
	resetAllLEDs();
	for (int i = 0; i < 4; i++) {
		BSP_LED_On(LED_GREEN_2);
		HAL_Delay(500);
		BSP_LED_Off(LED_GREEN_2);
		HAL_Delay(500);
	}
}

void setYellowLED() {
	resetAllLEDs();
	BSP_LED_On(LED_ORANGE);
	HAL_Delay(2000);
}

void setBlinkingYellowLED() {
	resetAllLEDs();
	for (int i = 0; i < 4; i++) {
		BSP_LED_On(LED_ORANGE);
		HAL_Delay(500);
		BSP_LED_Off(LED_ORANGE);
		HAL_Delay(500);
	}
}
