#include "limits.h"

/*@ requires \valid(a);
  @ requires *a+1 <= INT_MAX;
  @ ensures *a == \old(*a+1);
  @ assigns *a;
*/
void add_one(int *a);

/*@ assigns \nothing;
  */
void dummy();

/*@ requires \valid(a) && \valid(b);
  @ requires *a+1 <= INT_MAX;
  @ requires \separated(a,b);
  @ assigns *a;
  @ ensures \result == 1;
  @ ensures *a == \old(*a+1);
  @ ensures *b == \old(*b);
*/
int f(int *a, int *b);