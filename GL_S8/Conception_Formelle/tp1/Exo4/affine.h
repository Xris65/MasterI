#include "limits.h"

/* Écrivez ici votre spécification. Attention, elle doit permettre de prouver les assertions RTE.*/
/*@ requires a * x >= -INT_MAX;
    requires a * x <= INT_MAX;
    requires (a * x) + b >= -INT_MAX;
    requires (a * x) + b <= INT_MAX;
    */
int f(int a,int b,int x);