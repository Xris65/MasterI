#include "syscall.h"

void thread_fun(){
    PutString("Hello from thread\n");
}

int main() {
    char c = 'a'; //Just to avoir errors, as threadCreate needs an argument
    ThreadCreate(thread_fun, (void*)c);
    ThreadCreate(thread_fun, (void*)c);
    ThreadCreate(thread_fun, (void*)c);
    ThreadCreate(thread_fun, (void*)c);
    ThreadExit();
    return 0;
}
