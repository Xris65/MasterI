#include "syscall.h"

/*
 * Affiche l'alphabet grâce à PutChar, caractère par caractère.
 */

void print(char c, int n) {
  int i;
  for (i = 0; i < n; i++) {
    PutChar(c + i);
  }
  PutChar('\n');
}
int main() {
  print('a', 26);
  return 0;
}
