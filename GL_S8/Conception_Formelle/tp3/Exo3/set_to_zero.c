#include "set_to_zero.h"

void set_to_zero(int *tab, int n)
{
/*@ loop invariant 0 <= i <= n;
	loop assigns tab[0 .. n -1], i;
	loop  invariant \forall integer j;(0 <= j < i) ==> tab[j] == 0;

*/
	for (int i = 0; i < n; i++)
	{
		tab[i] = 0;
	}
}