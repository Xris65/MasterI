// exception.cc 
//      Entry point into the Nachos kernel from user programs.
//      There are two kinds of things that can cause control to
//      transfer back to here from user code:
//
//      syscall -- The user code explicitly requests to call a procedure
//      in the Nachos kernel.  Right now, the only function we support is
//      "Halt".
//
//      exceptions -- The user code does something that the CPU can't handle.
//      For instance, accessing memory that doesn't exist, arithmetic errors,
//      etc.  
//
//      Interrupts (which can also cause control to transfer from user
//      code into the Nachos kernel) are handled elsewhere.
//
// For now, this only handles the Halt() system call.
// Everything else core dumps.
//
// Copyright (c) 1992-1993 The Regents of the University of California.
// All rights reserved.  See copyright.h for copyright notice and limitation 
// of liability and disclaimer of warranty provisions.

#include "copyright.h"
#include "system.h"
#include "syscall.h"

#ifdef CHANGED
#include "userthread.h"
#endif

//----------------------------------------------------------------------
// UpdatePC : Increments the Program Counter register in order to resume
// the user program immediately after the "syscall" instruction.
//----------------------------------------------------------------------
static void
UpdatePC ()
{
    int pc = machine->ReadRegister (PCReg);
    machine->WriteRegister (PrevPCReg, pc);
    pc = machine->ReadRegister (NextPCReg);
    machine->WriteRegister (PCReg, pc);
    pc += 4;
    machine->WriteRegister (NextPCReg, pc);
}


//----------------------------------------------------------------------
// ExceptionHandler
//      Entry point into the Nachos kernel.  Called when a user program
//      is executing, and either does a syscall, or generates an addressing
//      or arithmetic exception.
//
//      For system calls, the following is the calling convention:
//
//      system call code -- r2
//              arg1 -- r4
//              arg2 -- r5
//              arg3 -- r6
//              arg4 -- r7
//
//      The result of the system call, if any, must be put back into r2. 
//
// And don't forget to increment the pc before returning. (Or else you'll
// loop making the same system call forever!
//
//      "which" is the kind of exception.  The list of possible exceptions 
//      are in machine.h.
//----------------------------------------------------------------------




void
ExceptionHandler (ExceptionType which)
{
    int type = machine->ReadRegister (2);
    int address = machine->registers[BadVAddrReg];

    switch (which)
      {
	case SyscallException:
          {
	    switch (type)
	      {
		case SC_Halt:
		  {
		    DEBUG ('s', "Shutdown, initiated by user program.\n");
		    printf("Je suis passe par Halt \n");
			interrupt->Powerdown ();
		    break;
		  }
		case SC_Exit:
		{
			DEBUG ('s', "Exit, initiated by user program.\n");
		  	printf("Exit !\n");
			int exitCode = machine->ReadRegister(4);
			printf("Exit code is %d\n",exitCode);
			interrupt->Powerdown ();
			break;
		}
		#ifdef CHANGED
		case SC_Yield:
		{
			DEBUG ('s', "Yield, initiated by user program.\n");
			printf("Yield !\n");
			currentThread->Yield();
			break;
		}
		case SC_PutChar:
		{
			DEBUG ('s', "PutChar, initiated by user program.\n");
			char toPut = machine->ReadRegister(4);
			consoledriver->PutChar(toPut);
			break;
		}
		case SC_PutString:
		{
			DEBUG('s', "PutString, initiated by user program.\n");
			int readFrom = machine->ReadRegister(4);
			char string[MAX_STRING_SIZE];
			machine->copyStringFromMachine(readFrom, string, MAX_STRING_SIZE);
			consoledriver->PutString(string);
			break;
		}
		case SC_GetChar:
		{
			DEBUG('s', "GetChar, initiated by user program.\n");
			int toPut = consoledriver->GetChar();
			machine->WriteRegister(2, toPut);
			break;
		}
		case SC_GetString:
		{
			DEBUG('s', "GetString, initiated by user program.\n");
			int writeTo = machine->ReadRegister(4);
			char string[MAX_STRING_SIZE];
			consoledriver->GetString(string, MAX_STRING_SIZE);
			machine->copyStringToMachine(writeTo, string, MAX_STRING_SIZE);
			break;
		}
		case SC_PutInt:
		{
			DEBUG ('s', "PutInt, initiated by user program.\n");
			int toPut = machine->ReadRegister(4);
			char buffer[MAX_STRING_SIZE];
			snprintf(buffer, MAX_STRING_SIZE,"%d\n", toPut);
			consoledriver->PutString(buffer);
			break;
		}
		case SC_GetInt:
		{
			DEBUG('s', "GetInt, initiated by user program.\n");
			int writeTo = machine->ReadRegister(4); // adresse
			char buffer[MAX_STRING_SIZE];
			int toWrite;
			consoledriver->GetString(buffer, MAX_STRING_SIZE);
			sscanf(buffer,"%d", &toWrite);
			machine->WriteMem(writeTo, 1, toWrite);
			break;
		}
		case SC_ThreadCreate:
		{
			DEBUG ('s', "ThreadCreate, initiated by user program.\n");
			int f = machine->ReadRegister(4);
			int arg = machine->ReadRegister(5);
			do_ThreadCreate(f, arg);
			break;
		}
		case SC_ThreadExit:
		{
			DEBUG ('s', "ThreadExit, initiated by user program.\n");
			do_ThreadExit();
			break;
		}
		#endif
		default:
		  {
		    printf("Unimplemented system call %d\n", type);
		    ASSERT(FALSE);
		  }
	      }

	    // Do not forget to increment the pc before returning!
	    UpdatePC ();
	    break;
	  }

	case PageFaultException:
	  if (!address) {
	    printf("NULL dereference at PC %x!\n", machine->registers[PCReg]);
	    ASSERT (FALSE);
	  } else {
	    printf ("Page Fault at address %x at PC %x\n", address, machine->registers[PCReg]);
	    ASSERT (FALSE);	// For now
	  }
	  break;

	case ReadOnlyException:
	  printf ("Read-Only at address %x at PC %x\n", address, machine->registers[PCReg]);
	  ASSERT (FALSE);	// For now
	  break;

	case BusErrorException:
	  printf ("Invalid physical address at address %x at PC %x\n", address, machine->registers[PCReg]);
	  ASSERT (FALSE);	// For now
	  break;

	case AddressErrorException:
	  printf ("Invalid address %x at PC %x\n", address, machine->registers[PCReg]);
	  ASSERT (FALSE);	// For now
	  break;

	case OverflowException:
	  printf ("Overflow at PC %x\n", machine->registers[PCReg]);
	  ASSERT (FALSE);	// For now
	  break;

	case IllegalInstrException:
	  printf ("Illegal instruction at PC %x\n", machine->registers[PCReg]);
	  ASSERT (FALSE);	// For now
	  break;

	default:
	  printf ("Unexpected user mode exception %d %d %x at PC %x\n", which, type, address, machine->registers[PCReg]);
	  ASSERT (FALSE);
	  break;
      }
}
