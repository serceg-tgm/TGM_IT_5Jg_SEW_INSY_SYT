/**
  ******************************************************************************
  * @file    	state_centric.c
  * @author  	Stefan Erceg
  * @version 	20151030 <br>
  * @brief		Umsetzung einer State-Centric State Machine
  *
  * In diesem File existiert die Funktion, in welcher die Zustaende der LEDs und die Events mittels Umsetzung einer
  * State-Centric State Machine in einem switch-case Konstrukt verwaltet werden.
 ******************************************************************************
*/

#include <stdbool.h>

#include "LED_States.h"
#include "state_machine.h"

void trafficLightSystem(currentTrafficLight* t_light) {
	/* Es wird eine Variable fuer die Timer-Interrupts erstellt, welche hochgezaehlt wird, um einen Counter
	 * darzustellen. */
	static int counter = 0;
	counter++;

	// Das switch-case Konstrukt wird nun aufgebaut.
	switch (t_light->currentState) {
	case RED:
		// Falls der Nachtmodus aktiviert ist, wird der Zustand zu "gelb blinkend" gewechselt.
		if (t_light->night) {
			resetAllLEDs();
			t_light->currentState = YELLOW_BLINK;
			t_light->currentEvent = ERR;
		}
		/* Falls das aktuelle Event "STOP" ist, wird die rote LED eingeschaltet und gewartet, bis der Counter
		 * 2000, also 2 Sekunden, erreicht hat. Danach wird zum naechsten Status gewechselt.
		 */
		if (t_light->currentEvent == STOP) {
			setRedLED();
			if (counter >= 2000) {
				counter = 0;
				t_light->currentEvent = PREPAREFORGOING;
				t_light->currentState = RED_YELLOW;
			}
		}
		break;
	case RED_YELLOW:
		/* Falls das aktuelle Event "PREPAREFORGOING" ist, werden die rote und gelbe LED eingeschaltet und
		 * gewartet, bis der Counter 1600, also 1,6 Sekunden, erreicht hat. Danach wird zum naechsten Status
		 * gewechselt.
		 */
		if (t_light->currentEvent == PREPAREFORGOING) {
			setRedYellowLEDs();
			if (counter >= 1600) {
				counter = 0;
				t_light->currentEvent = GO;
				t_light->currentState = GREEN;
			}
		}
		break;
	case GREEN:
		/* Falls das aktuelle Event "GO" ist, wird die gruene LED eingeschaltet und gewartet, bis der Counter
		 * 2000, also 2 Sekunden, erreicht hat. Danach wird zum naechsten Status gewechselt.
		 */
		if (t_light->currentEvent == GO) {
			setGreenLED();
			if (counter >= 2000) {
				counter = 0;
				t_light->currentEvent = PREPAREFORWAITING;
				t_light->currentState = GREEN_BLINK;
			}
		}
		break;
	case GREEN_BLINK:
		/* Falls das aktuelle Event "PREPAREFORWAITING" ist, wird die gruene LED alle 500 ms umgeschaltet, bis
		 * der Counter 9 betraegt.
		 */
		if (t_light->currentEvent == PREPAREFORWAITING) {
			if (t_light->blink_counter >= 9) {
				t_light->blink_counter = 0;
				t_light->currentEvent = CAUTION;
				t_light->currentState = YELLOW;
			} else {
				if (counter >= 500) {
					counter = 0;
					t_light->blink_counter += 1;
					toggleGreenLED();
				}
			}
		}
		break;
	case YELLOW:
		/* Falls das aktuelle Event "CAUTION" ist, wird die gelbe LED eingeschaltet und gewartet, bis der Counter
		 * 1800, also 1,8 Sekunden, erreicht hat. Danach wird zum naechsten Status gewechselt.
		 */
		if (t_light->currentEvent == CAUTION) {
			setYellowLED();
			if (counter >= 1800) {
				counter = 0;
				t_light->currentState = RED;
				t_light->currentEvent = STOP;
			}
		}
		break;
	case YELLOW_BLINK:
		/* Falls das aktuelle Event "CAUTION" ist, wird die gelbe LED eingeschaltet und gewartet, bis der Counter
		 * 1800, also 1,8 Sekunden, erreicht hat. Danach wird zum naechsten Status gewechselt.
		 */
		/* Die gelbe LED wird alle 500 ms umgeschaltet. Danach wird der aktuelle Zustand wieder auf rot gesetzt
		 * und das Event auf "STOP". Somit faengt der Algorithmus von vorne an.
		 */
		t_light->currentState = YELLOW_BLINK;
		if (counter >= 500) {
			counter = 0;
			toggleYellowLED();
		}
		break;
	default:
		t_light->currentState = RED;
		t_light->currentEvent = STOP;
		break;
	}
}
