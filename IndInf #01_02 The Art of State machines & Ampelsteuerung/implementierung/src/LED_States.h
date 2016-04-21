/**
  ******************************************************************************
  * @file		LED_States.h
  * @author  	Stefan Erceg
  * @version 	20150928 <br>
  * @brief		Hier werden die verschiedenen Funktionen zum Verwalten der Zustaende der LEDs definiert.
  *
  * In dem Header-File werden alle Funktionen definiert, die fuer das Leuchten der spezifischen LEDs
  * zustaendig sind. Ebenfalls existiert eine Funktion zum Reseten der LEDs.
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
 * In dieser Funktion wird die rote LED aktiviert und danach eine Verzoegerung von 2 Sekunden eingestellt.
 */
void setRedLED();

/**
 * @brief		Die rote und gelbe LED werden aktiviert.
 *
 * In dieser Funktion werden die rote und gelbe LED aktiviert und danach eine Verzoegerung von 2 Sekunden eingestellt.
 */
void setRedYellowLEDs();

/**
 * @brief		Die gruene LED wird aktiviert.
 *
 * In dieser Funktion wird die gruene LED aktiviert und danach eine Verzoegerung von 2 Sekunden eingestellt.
 */
void setGreenLED();

/**
 * @brief		Die gruene LED wird auf blinkend gesetzt.
 *
 * In dieser Funktion wird die gruene LED mittels einer for-Schleife auf blinkend gesetzt. Nachdem die gruene LED
 * ein- bzw. ausgeschaltet wird, wird eine Verzoegerung von 0,5 Sekunden eingestellt.
 */
void setBlinkingGreenLED();

/**
 * @brief		Die gelbe LED wird aktiviert.
 *
 * In dieser Funktion wird die gelbe LED aktiviert und danach eine Verzoegerung von 2 Sekunden eingestellt.
 */
void setYellowLED();

/**
 * @brief		Die gelbe LED wird auf blinkend gesetzt.
 *
 * In dieser Funktion wird die gelbe LED mittels einer for-Schleife auf blinkend gesetzt. Nachdem die gelbe LED
 * ein- bzw. ausgeschaltet wird, wird eine Verzoegerung von 0,5 Sekunden eingestellt.
 */
void setBlinkingYellowLED();

#endif /* LED_STATES_H_ */
