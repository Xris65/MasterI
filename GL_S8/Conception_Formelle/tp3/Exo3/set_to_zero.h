#include "limits.h"

/* Cette fonction assure que pour tout i, si i est entre 0 et n-1, tab[i] = 0.
Elle assure également qu’il existe au moins une telle case.
En ACSL, pour tout i s’écrit "\forall int i;", et il existe i, "\exists int i;".
*/

/*@ requires \valid(tab + (0 .. n-1));
    requires n >= 0 ;
    
  
    ensures \forall integer i; (0 <= i <= n-1) ==> tab[i] == 0; 
*/
void set_to_zero(int *tab, int n);