/**
  ******************************************************************************
  * @file		LED_States.h
  * @author  	Stefan Erceg
  * @version 	20151030 <br>
  * @brief		Hier werden die verschiedenen Funktionen zum Verwalten der Zustaende der LEDs definiert.
  *
  * In dem Header-File werden alle Funktionen definiert, die fuer das Leuchten der spezifischen LEDs
  * zustaendig sind. Ebenfalls existieren Funktionen zum Togglen der LEDs und eine Funktion zum Reseten aller LEDs.
  ******************************************************************************
*/

#ifndef LED_STATES_H_
#define LED_STATES_H_

/**
 * @brief		Alle LEDs werden auf "Off" gesetzt.
 *
 * Diese Funktion dient zum Ausschalten aller LEDs und wird zu Beginn aller setLED-Funktionen aufgerufen.
 */
void resetAllLEDs();

/**
 * @brief		Die rote LED wird aktiviert.
 *
 * In dieser Funktion wird die rote LED aktiviert. Davor werden alle LEDs auf "Off" gesetzt.
 */
void setRedLED();

/**
 * @brief		Die rote und gelbe LED werden aktiviert.
 *
 * In dieser Funktion werden die rote und gelbe LED aktiviert. Davor werden alle LEDs auf "Off" gesetzt.
 */
void setRedYellowLEDs();

/**
 * @brief		Die rote LED wird hier umgeschaltet.
 *
 * In dieser Funktion wird die rote LED umgeschaltet. Dazu wird die Funktion "BSP_LED_Toggle" aufgerufen.
 */
void toggleRedLED();

/**
 * @brief		Die gruene LED wird aktiviert.
 *
 * In dieser Funktion wird die gruene LED aktiviert. Davor werden alle LEDs auf "Off" gesetzt.
 */
void setGreenLED();

/**
 * @brief		Die gruene LED wird auf blinkend gesetzt.
 *
 * In dieser Funktion wird die gruene LED auf- und abgedreht. Davor werden alle LEDs auf "Off" gesetzt.
 */
void setBlinkingGreenLED();

/**
 * @brief		Die gruene LED wird hier umgeschaltet.
 *
 * In dieser Funktion wird die gruene LED umgeschaltet. Dazu wird die Funktion "BSP_LED_Toggle" aufgerufen.
 */
void toggleGreenLED();

/**
 * @brief		Die gelbe LED wird aktiviert.
 *
 * In dieser Funktion wird die gelbe LED aktiviert. Davor werden alle LEDs auf "Off" gesetzt.
 */
void setYellowLED();

/**
 * @brief		Die gelbe LED wird auf blinkend gesetzt.
 *
 * In dieser Funktion wird die gelbe LED auf- und abgedreht. Davor werden alle LEDs auf "Off" gesetzt.
 */
void setBlinkingYellowLED();

/**
 * @brief		Die gelbe LED wird hier umgeschaltet.
 *
 * In dieser Funktion wird die gelbe LED umgeschaltet. Dazu wird die Funktion "BSP_LED_Toggle" aufgerufen.
 */
void toggleYellowLED();

#endif /* LED_STATES_H_ */
