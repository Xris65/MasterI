#include <limits.h>
/*@ 
requires a < INT_MAX && a > 0; 
ensures \result >= 0;
*/
int plus_one(int a);