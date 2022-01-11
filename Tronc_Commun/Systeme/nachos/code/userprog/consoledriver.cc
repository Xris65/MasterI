#ifdef CHANGED
#include "consoledriver.h"
#include "copyright.h"
#include "synch.h"
#include "system.h"

static Semaphore *readAvail;
static Semaphore *writeDone;
static Lock *lock;



static void ReadAvailHandler(void *arg)
{
	(void)arg;
	readAvail->V();
}
static void WriteDoneHandler(void *arg)
{
	(void)arg;
	writeDone->V();
}
ConsoleDriver::ConsoleDriver(const char *in, const char *out)
{
	readAvail = new Semaphore("read avail", 0);
	writeDone = new Semaphore("write done", 0);
	lock = new Lock("read/write protect");
	console   = new Console (in, out, ReadAvailHandler, WriteDoneHandler, NULL);
}
ConsoleDriver::~ConsoleDriver()
{
	delete console;
	delete writeDone;
	delete readAvail;
	delete lock;
}
void ConsoleDriver::PutChar(int ch)
{
	lock->Acquire();
	_PutChar(ch);
	lock->Release();
}

void ConsoleDriver::_PutChar(int ch)
{
	console->TX(ch);
	writeDone->P();
}

int ConsoleDriver::_GetChar(){
    readAvail->P();	// wait for character to arrive
	return console->RX();
}

int ConsoleDriver::GetChar(){
	lock->Acquire();
	char ch = _GetChar();
	lock->Release();
	return ch;
}


void ConsoleDriver::PutString(const char s[])
{
	lock->Acquire();
	int i = 0;
	while (s[i] != '\0'){
		char toPut = s[i++];
		_PutChar(toPut);
	}
	lock->Release();
}

/*
void ConsoleDriver::GetString(char *s, int n)
{
	int read;
	for(read = 0; read < n-1; read++){
		s[read] = GetChar();
		if(s[read] == '\0' || s[read] == '\n') {
			break;
		}
	}

	s[read] = '\0';
}
*/
void ConsoleDriver::GetString(char *s, int n)
{
	lock->Acquire();
	int i = 0;
	while (i < n) {
		s[i] = _GetChar();
		if (s[i] == '\n' || s[i] == -1) {
			if(s[i] == '\n')
				i++;
			break;
		}
		i++;
	}
	s[i] = '\0';
	lock->Release();
}

#endif // CHANGED