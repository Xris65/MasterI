#include "syscall.h"

int main() {
  // doit ne rien afficher
  PutString("");
  // doit afficher Hello World!\n
  PutString("Hello world!\n");
  //  doit afficher 15 caracteres avec un \0 a la fin
  // soit 123456789012345\n
  PutString("1234567890123456789012345678901234567890\n");
  return 0;
}
