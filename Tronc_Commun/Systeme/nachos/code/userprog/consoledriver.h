#ifdef CHANGED
#ifndef CONSOLEDRIVER_H
#define CONSOLEDRIVER_H
#include "console.h"
#include "copyright.h"
#include "utility.h"
class ConsoleDriver : dontcopythis
{
  public: // initialize the hardware console device3
	ConsoleDriver(const char *readFile, const char *writeFile);
	~ConsoleDriver();             // clean up
	void PutChar(int ch);         // Behaves like putchar(3S)
	void _PutChar(int ch);                 // Used by PutChar()
	int GetChar();                 // Behaves like getchar(3S)
	int _GetChar();                 // Used by GetChar()
	void PutString(const char *s); // Behaves like fputs(3S)
	void GetString(char *s, int n); // Behaves like fgets(3S)
  private:
	Console *console;
};
#endif // CONSOLEDRIVER_H
#endif // CHANGED