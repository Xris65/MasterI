#include "syscall.h"

/**
 * Tests de plusieurs valeurs d'entiers, intéressantes, comme par exemple un
 * nombre négatif, la valeur de int max et un overflow. 0 est aussi un bon
 * test.
 */

int main() {
  PutInt(-1);
  PutInt(2147483647);
  PutInt(2147483647 + 1); // overflow, devrait afficher -2147483648
  PutInt(0);
  return 0;
}
