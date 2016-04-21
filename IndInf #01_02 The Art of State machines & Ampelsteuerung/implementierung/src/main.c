/**
  ******************************************************************************
  * @file    	main.c
  * @author  	Stefan Erceg
  * @version 	20150928 <br>
  * @brief		Dieses File ruft die Funktion einer bestimmten State auf.
  *
  * In diesem File ist die main-Funktion vorhanden, in welcher der STM und die LEDs initialisiert und die Funktion
  * einer bestimmten State Machine (defaultmaessig: Event Centric State Machine) aufgerufen wird.
  ******************************************************************************
*/

#include "stm32f3xx.h"
#include "stm32f3_discovery.h"

#include "state_machine.h"

/**
 * @brief		Hier erfolgt die Initialisierung des STMs und der LEDs.
 *
 * In der Main-Funktion werden der STM und die LEDs initialisiert und die Funktion einer bestimmten State
 * Machine (defaultmaessig: Event Centric State Machine) aufgerufen.
 */
int main(void) {
	// STM wird initialisiert
	SystemInit();
	SystemCoreClockUpdate();

	SysTick_Config(SystemCoreClock / 1000);

	// benoetigte LEDs werden initialisiert
	BSP_LED_Init(LED_RED);
	BSP_LED_Init(LED_ORANGE);
	BSP_LED_Init(LED_GREEN_2);

	// Variablen, die als Parameter beim Aufruf der State Machine-Funktionen uebergeben werden, werden erstellt
	currentTrafficLight trafficLight;
	currentTrafficLight* pointerForTrafficLight = &trafficLight;

	while (1) {
		// in dieser Endlosschleife wird die Funktion der jeweiligen State Machine aufgerufen
		// hier wurde default-maessig die Event Centric State Machine aufgerufen
		trafficLightSystemWithEvent(pointerForTrafficLight);
	}
}

