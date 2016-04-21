/**
  ******************************************************************************
  * @file    	state_centric.c
  * @author  	Stefan Erceg
  * @version 	20151001 <br>
  * @brief		Umsetzung einer State-Centric State Machine
  *
  * In diesem File existiert die Funktion, in welcher die Zustaende der LEDs und die Events mittels Umsetzung einer
  * State-Centric State Machine in einem switch-case Konstrukt verwaltet werden.
 ******************************************************************************
*/

#include "state_machine.h"

void trafficLightSystem(currentTrafficLight* t_light) {
	switch (t_light->currentState) {
	case RED:
		if (t_light->currentEvent == STOP) {
			t_light->currentEvent = PREPAREFORGOING;
			setRedLED();
			t_light->currentState = RED_YELLOW;
		}
		break;
	case RED_YELLOW:
		if (t_light->currentEvent == PREPAREFORGOING) {
			t_light->currentEvent = GO;
			setRedYellowLEDs();
			t_light->currentState = GREEN;
		}
		break;
	case GREEN:
		if (t_light->currentEvent == GO) {
			t_light->currentEvent = PREPAREFORWAITING;
			setGreenLED();
			t_light->currentState = GREEN_BLINK;
		}
		break;
	case GREEN_BLINK:
		if (t_light->currentEvent == PREPAREFORWAITING) {
			t_light->currentEvent = CAUTION;
			setBlinkingGreenLED();
			t_light->currentState = YELLOW;
		}
		break;
	case YELLOW:
		if (t_light->currentEvent == CAUTION) {
			t_light->currentEvent = STOP;
			setYellowLED();
			t_light->currentState = RED;
		}
		break;
	case YELLOW_BLINK:
		setBlinkingYellowLED();
		t_light->currentState = YELLOW_BLINK;
		break;
	default:
		t_light->currentState = RED;
		break;
	}
}
