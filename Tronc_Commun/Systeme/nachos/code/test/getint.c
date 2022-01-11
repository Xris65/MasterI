#include "syscall.h"
/*
 * L'entier rentré dans stdin est simplement réaffiché.
 */
int main(){
    int myInt;
    GetInt(&myInt);
    PutInt(myInt);
    return 0;
}
