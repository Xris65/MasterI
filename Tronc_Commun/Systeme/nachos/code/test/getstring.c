#include "syscall.h"
/**
 * Ce test renvoie la chaîne de caractère de taille maximale 10. Après ce
 * nombre, le résultat est indéfini.
 */
int main() {
  int size = 10;
  char s[size];
  GetString(s, size);
  PutString(s);
  return 0;
}
