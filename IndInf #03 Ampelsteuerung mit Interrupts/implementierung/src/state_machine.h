/**
  ******************************************************************************
  * @file    	state_machine.h
  * @author  	Stefan Erceg
  * @version 	20151030 <br>
  * @brief		Hier existieren Enums und Structs zum Definieren der Zustaende der LEDs und der Events.
  *
  * In dem Header-File werden Enums fuer die Zustaende der LEDs und die Events erstellt. Ebenfalls existiert ein
  * Struct, in dem der aktuelle Zustand und das aktuelle Event gespeichert werden.
  ******************************************************************************
*/

#ifndef STATE_MACHINE
#define STATE_MACHINE

// header-files, die benoetigt werden, werden inkludiert
#include <stdlib.h>
#include <stdbool.h>
#include <stdio.h>

#include "LED_States.h"

/**
 * @brief 		Enums fuer die verschiedenenen Zustaende der LEDs werden erstellt.
 *
 * Alle moeglichen Zustaende der LEDs, wie z.B. nur rote LED aktiv oder rote und gelbe LEDs aktiv, werden hier
 * definiert.
 */
typedef enum {
	RED,				/**< Enum fuer die rote LED */
	RED_YELLOW,			/**< Enum fuer die rote und gelbe LED */
	GREEN,				/**< Enum fuer die gruene LED */
	GREEN_BLINK,		/**< Enum fuer die blinkende gruene LED */
	YELLOW,				/**< Enum fuer die gelbe LED */
	YELLOW_BLINK		/**< Enum fuer die blinkende gelbe LED */
} LEDstate;

/**
 * @brief 		Enums fuer die verschiedenenen Events werden erstellt.
 *
 * Alle moeglichen Events fuer die LEDs werden hier definiert.
 */
typedef enum {
	STOP,				/**< Enum fuer das Event "gelb zu rot" */
	PREPAREFORGOING,	/**< Enum fuer das Event "rot zu rot-gelb" */
	GO,					/**< Enum fuer das Event "rot-gelb zu gruen" */
	PREPAREFORWAITING,	/**< Enum fuer das Event "gruen zu gruen-blinkend" */
	CAUTION,			/**< Enum fuer das Event "gruen-blinkend zu gelb" */
	ERR					/**< Enum fuer den Status "Error" falls etwas schief gegangen ist */
} LEDevent;

/**
 * @brief 		Struct zur Speicherung des aktuellen Zustands und des aktuellen Events
 *
 * Der aktuelle Zustand und das aktuelle Event werden hier gespeichert. Ebenfalls werden hier Variablen fuer den
 * Counter und die Zustaende "Unterbrochen" und "Nachtmodus" abgelegt.
 */
typedef struct {
	LEDstate currentState;		/**< Variable fuer den aktuellen Zustand */
	LEDevent currentEvent;		/**< Variable fuer das aktuelle Event */
	int blink_counter;			/**< Variable fuer den Counter */
	bool interrupted;			/**< Variable fuer die Ueberprufeung "Unterbrochen" oder nicht */
	bool night;					/**< Variable fuer die Ueberprufeung "Nachtmodus aktiviert" oder nicht */
} currentTrafficLight;

/**
 * @brief		Funktion zum Ausfuehren einer State-Centric State Machine
 * @param		t_light dient zum Verwalten des aktuellen Zustands der LED und des aktuellen Events
 *
 * Das Konzept einer State-Centric State Machine wird hier umgesetzt. Dabei wird ein switch-case Konstrukt
 * aufgebaut, bei der der Status zu Beginn und in einer if-Abfrage das Event geprueft werden. Der Status aendert
 * sich, sobald das spezifische LED Delay ausgelaufen ist.
 */
void trafficLightSystem(currentTrafficLight* t_light);

#endif /* STATE_MACHINE */
