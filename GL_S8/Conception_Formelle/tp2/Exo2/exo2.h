#include "limits.h"

/*@ requires \valid(a);
    requires \valid(b);
    ensures (\old(a) == b && \old(b) == a) ||(\old(a) == a && \old(b) == b) ;
*/
void sort_ptr(int* a, int* b);

/*@ requires \valid(a);
    requires \valid_read(b);
    requires \separated(a,b);
    requires *a + *b <= INT_MAX;
    requires *a + *b >= INT_MIN;
    ensures *a == \old(*a) + *b;
*/
void sum_in_pointer(int* a, int* b);