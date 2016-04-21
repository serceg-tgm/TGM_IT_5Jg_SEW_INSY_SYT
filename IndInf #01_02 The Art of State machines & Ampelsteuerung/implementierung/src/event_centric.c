/**
  ******************************************************************************
  * @file    	event_centric.c
  * @author  	Stefan Erceg
  * @version 	20151001 <br>
  * @brief		Umsetzung einer Event-Centric State Machine
  *
  * In diesem File existiert die Funktion, in welcher die Zustaende der LEDs und die Events mittels Umsetzung einer
  * Event-Centric State Machine in einem switch-case Konstrukt verwaltet werden.
 ******************************************************************************
*/

#include "state_machine.h"

void trafficLightSystemWithEvent(currentTrafficLight* t_light) {
	switch (t_light->currentEvent) {
	case STOP:
		if (t_light->currentState == RED) {
			setRedLED();
			t_light->currentState = RED_YELLOW;
			t_light->currentEvent = PREPAREFORGOING;
		} else {
			resetAllLEDs();
			t_light->currentEvent = ERR;
		}
		break;
	case PREPAREFORGOING:
		if (t_light->currentState == RED_YELLOW) {
			setRedYellowLEDs();
			t_light->currentState = GREEN;
			t_light->currentEvent = GO;
		} else {
			resetAllLEDs();
			t_light->currentEvent = ERR;
		}
		break;
	case GO:
		if (t_light->currentState == GREEN) {
			setGreenLED();
			t_light->currentState = GREEN_BLINK;
			t_light->currentEvent = PREPAREFORWAITING;
		} else {
			resetAllLEDs();
			t_light->currentEvent = ERR;
		}
		break;
	case PREPAREFORWAITING:
		if (t_light->currentState == GREEN_BLINK) {
			setBlinkingGreenLED();
			t_light->currentEvent = CAUTION;
			t_light->currentState = YELLOW;
		} else {
			resetAllLEDs();
			t_light->currentEvent = ERR;
		}
		break;
	case CAUTION:
		if (t_light->currentState == YELLOW) {
			setYellowLED();
			t_light->currentEvent = STOP;
			t_light->currentState = RED;
		}
		break;
	case ERR:
		setBlinkingYellowLED();
		t_light->currentState = YELLOW_BLINK;
		break;
	default:
		t_light->currentState = RED;
		break;
	}
}
