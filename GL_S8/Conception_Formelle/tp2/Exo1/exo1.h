#include "limits.h"

/*@ requires \valid_read(a) && \valid_read(b);
    ensures \result == *a || \result == *b;
    ensures \result >= *a;
    ensures \result >= *b;
*/
int max_ptr(int *a, int *b);

/*@ requires \valid_read(a);
    requires n >= 0;
    requires \valid_read(a + (int) n - 1);
    requires a[0] + a[n-1] >= INT_MIN;
    requires a[0] + a[n-1] <= INT_MAX;

    ensures \result == \old(a[0]) + \old(a[n-1]);
*/
int sum_first_last(int *a, int n);

/*@ requires \valid(a);
    requires n - 1 >= INT_MIN;
    requires \valid(a + n - 1);
    ensures  \old(a[0]) == a[n-1];
    ensures  \old(a[n-1]) == a[0];
*/

void swap_first_last(int *a, int n);

/*@ requires \valid(a);
    requires \valid(b);
    ensures  \old(*a) == *b;
    ensures  \old(*b) == *a;
*/

void swap(int *a, int *b);