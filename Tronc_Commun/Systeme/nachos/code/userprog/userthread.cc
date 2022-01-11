#ifdef CHANGED
#include "thread.h"
#include "system.h"
#include "syscall.h"
#include "synch.h"

Lock *lock = new Lock("thread lock");

void StartUserThread(void *args_array)
{
    int *args = (int *)args_array;
    int f = args[0];
    int arg = args[1];
    delete[] args;
    int stackIndex = currentThread->space->AllocateUserStack();
    if (stackIndex == -1) {
        lock->Release();
        return;
    }
    for (int i = 0; i < NumTotalRegs; i++)
        machine->WriteRegister(i, 0);
    int stack = currentThread->space->NumPages() * PageSize - (stackIndex * 256);
    DEBUG('t', "User thread stack index = %d\n", stackIndex);
    currentThread->threadID = stackIndex;
    machine->WriteRegister(StackReg, stack);
    machine->WriteRegister(PCReg, THREAD_START_ADDRESS);
    machine->WriteRegister (NextPCReg, machine->ReadRegister(PCReg) + 4);
    machine->WriteRegister(2, f);
    machine->WriteRegister(4, arg);
    lock->Release();
    machine->Run();
}

int do_ThreadCreate(int f, int arg)
{
    int *args = new int[2]{f, arg};
    Thread *t = new Thread("userThread");
    t->space = currentThread->space;
    lock->Acquire();
    if(currentThread->space->SpaceAvailable()){
        currentThread->space->threadCount++;
        t->Start(StartUserThread, args);
    }
    else{
        lock->Release();
        return -1;
    }
    return 0;
}

void do_ThreadExit()
{
    lock->Acquire();
    if (--currentThread->space->threadCount == 0)
        interrupt->Powerdown();
    DEBUG('t', "Thread %d exit\n", currentThread->threadID);
    currentThread->space->DeallocateUserStack(currentThread->threadID);
    lock->Release();
    currentThread->Finish();
}

#endif