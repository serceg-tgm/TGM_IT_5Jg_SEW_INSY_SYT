/**
  ******************************************************************************
  * @file		LED_States.c
  * @author  	Stefan Erceg
  * @version 	20151030 <br>
  * @brief		Hier werden die verschiedenen Zustaende der LEDs verwaltet.
  *
  * In diesem File existieren Funktionen, die fuer das Leuchten der spezifischen LEDs zustaendig sind. Ebenfalls
  * existieren Toggle-Funktionen zum Umschalten der LEDs. Zu Beginn jeder Set-Funktion werden alle LEDs reseted.
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
}

void setRedYellowLEDs() {
	resetAllLEDs();
	BSP_LED_On(LED_RED);
	BSP_LED_On(LED_ORANGE);
}

void toggleRedLED() {
	BSP_LED_Toggle(LED_RED);
}

void setGreenLED() {
	resetAllLEDs();
	BSP_LED_On(LED_GREEN_2);
}

void setBlinkingGreenLED() {
	resetAllLEDs();
	BSP_LED_On(LED_GREEN_2);
	BSP_LED_Off(LED_GREEN_2);
}

void toggleGreenLED() {
	BSP_LED_Toggle(LED_GREEN_2);
}

void setYellowLED() {
	resetAllLEDs();
	BSP_LED_On(LED_ORANGE);
}

void setBlinkingYellowLED() {
	resetAllLEDs();
	BSP_LED_On(LED_ORANGE);
	BSP_LED_Off(LED_ORANGE);
}

void toggleYellowLED() {
	BSP_LED_Toggle(LED_ORANGE);
}
