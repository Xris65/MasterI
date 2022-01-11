#include "syscall.h"
/*
 * getchar devrait simplement retourner le caractère entré. Dans un while,
 * getChar attends un \n pour récupérer sa valeur. Donc dans ce test,
 * getChar fonctionne comme un getString. Toute chaîne de caractère devrait
 * être renvoyée à l'identique.
 */

int main() {
      int c;
    while (1) {
        c = GetChar();
        PutChar(c);
    }
  return 0;
}
