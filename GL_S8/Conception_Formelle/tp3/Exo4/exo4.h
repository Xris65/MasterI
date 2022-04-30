#include "limits.h"

/*@ requires \valid(t + (0 .. n-1));
    requires n > 0;

    ensures \result <= n-1;
    ensures \result >= 0;
    ensures \forall integer i; (0 <= i <= n-1) ==> t[i] <= t[\result];
*/

int max_element(const int* t, int n);

/*Mettre la spécification ici.
*/
void sum_of_tab(int *a, int *b, int n);