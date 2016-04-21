/**
  ******************************************************************************
  * @file    	main.c
  * @author  	Stefan Erceg
  * @version 	20151030 <br>
  * @brief		Dieses File verwaltet die Interrupts und beinhaltet die Main-Funktion.
  *
  * In diesem File ist einerseits die Main-Funktion vorhanden, welche den STM und die LEDs initialisiert und
  * Default-Werte fuer die Variablen setzt. Anderseits existieren hier ebenfalls Funktionen zum Verwalten der
  * Interrupts.
  ******************************************************************************
*/

#include "stm32f3xx.h"
#include "stm32f3_discovery.h"

#include "state_machine.h"

/**
 * @brief		Die Funktion dient zum Konfigurieren der Interrupts.
 *
 * In dieser Funktion werden alle fuer die Interrupts relevanten Eigenschaften gesetzt. Es werden daher die
 * GPIO-Clock aktiviert, der User-Button konfiguriert und die Prioritaet fuer einen bestimmten Interrupt definiert.
 */
void EXTI0_Config();

currentTrafficLight trafficLight;				/**< stellt die Struct zum Verwalten der aktuellen Zustaende dar */
currentTrafficLight* pointerForTrafficLight;	/**< wird beim Aufruf der State Machine als Parameter uebergeben */

/**
 * @brief		Hier erfolgt unter anderem die Initialisierung des STMs und der LEDs.
 *
 * In der main-Funktion werden der STM und die LEDs initialisiert. Ebenfalls wird die EXTI0_Config-Methode
 * aufgerufen und Default-Werte fuer die Variablen des Structs, der unter anderem den aktuellen Zustand der LED
 * speichert, gesetzt.
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

	// EXTI0_Config-Methode wird aufgerufen
	EXTI0_Config();

	// Pointer wird initialisiert
	pointerForTrafficLight = &trafficLight;

	/* die Default-Werte fuer den Zustand, das Event, den Counter und die Ueberpruefung, ob der Nachtmodus aktiviert
	 * ist, werden gesetzt
	 */
	pointerForTrafficLight->currentEvent = STOP;
	pointerForTrafficLight->currentState = RED;
	pointerForTrafficLight->blink_counter = 0;
	pointerForTrafficLight->night = false;

	return EXIT_SUCCESS;
}

void EXTI0_Config(void) {
	GPIO_InitTypeDef GPIO_InitStructure;

	// GPIOA-Clock wird aktiviert
	__GPIOA_CLK_ENABLE();

	/* Der User-Button wird aktiviert - der Modus wird dabei auf Rising Edge eingestellt, d.h. der Button wird
	 * aktiviert, wenn das Clock Signal von 0 auf 1 geht.
	 */
	GPIO_InitStructure.Pin = GPIO_PIN_0;
	GPIO_InitStructure.Mode = GPIO_MODE_IT_RISING;
	GPIO_InitStructure.Pull = GPIO_NOPULL;
	GPIO_InitStructure.Speed = GPIO_SPEED_HIGH;
	HAL_GPIO_Init(GPIOA, &GPIO_InitStructure);

	// Die niedrigste Prioritaet wird fuer den EXTI0-Interrupt gesetzt. Danach wird der Interrupt aktiviert.
	HAL_NVIC_SetPriority(EXTI0_IRQn, 2, 0);
	HAL_NVIC_EnableIRQ(EXTI0_IRQn);
}

/**
 * @brief		Die Funktion dient zum Verwalten des Zustandes, nachdem der User-Button gedrueckt wurde.
 *
 * In dieser Funktion wird definiert, was das Programm ausfuehren soll, nachdem der User-Button gedrueckt wurde.
 * Dabei wird ueberprueft, ob der Nachtmodus aktiviert ist oder nicht und je nachdem wird der Zustand der LEDs
 * verwaltet.
 */
void HAL_GPIO_EXTI_Callback(uint16_t GPIO_Pin) {
	// Ueberpruefung, ob User-Button gedrueckt wurde
	if (GPIO_Pin == USER_BUTTON_PIN) {
		// Interrupt-Modus wird auf true gesetzt
		trafficLight.interrupted = true;
		if (trafficLight.interrupted) {
			/* Falls der Nachtmodus aktiviert ist, soll wieder zum normalen Zustand gewechselt werden.
			 * Ansonsten wird der Nachtmodus aktiviert.
			 */
			if (trafficLight.currentState == YELLOW_BLINK && pointerForTrafficLight->night == true) {
				pointerForTrafficLight->night = false;
				pointerForTrafficLight->currentState = RED;
				pointerForTrafficLight->currentEvent = STOP;
			} else {
				pointerForTrafficLight->night = true;
			}
			// Interrupt-Modus wird auf false gesetzt
			trafficLight.interrupted = false;
		}
	}
}

/**
 * @brief		Die Funktion dient zum Ausfuehren der Methode, welche eine State-Centric State Machine umsetzt.
 *
 * Diese Funktion wird jede Millisekunde aufgerufen. Dabei wird die Methode, welche die State-Centric State
 * Machine umsetzt, ausgefuehrt.
 */
void HAL_SYSTICK_Callback(void) {
	trafficLightSystem(pointerForTrafficLight);
}

