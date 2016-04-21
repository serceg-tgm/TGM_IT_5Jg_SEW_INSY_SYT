/**
  ******************************************************************************
  * @file    	state_centric_with_hidden_transitions.c
  * @author  	Stefan Erceg
  * @version 	20151001 <br>
  * @brief		Umsetzung einer State-Centric State Machine With Hidden Transitions
  *
  * In diesem File existiert die Funktion, in welcher die Zustaende der LEDs und die Events mittels Umsetzung einer
  * State-Centric State Machine With Hidden Transitions in einem switch-case Konstrukt verwaltet werden.
 ******************************************************************************
*/

#include "state_machine.h"

void trafficLightSystemWithTransition(currentTrafficLight* t_light) {
	switch (t_light->currentState) {
	case RED:
		setRedLED();
		t_light->currentState = RED_YELLOW;
		break;
	case RED_YELLOW:
		setRedYellowLEDs();
		t_light->currentState = GREEN;
		break;
	case GREEN:
		setGreenLED();
		t_light->currentState = GREEN_BLINK;
		break;
	case GREEN_BLINK:
		setBlinkingGreenLED();
		t_light->currentState = YELLOW;
		break;
	case YELLOW:
		setYellowLED();
		t_light->currentState = RED;
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
