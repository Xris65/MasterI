#include "nextPermutation.h"
#include "reverse.h"
#include "swap-xor.h"

bool NextPermutation(int *P, unsigned int n)
{
    unsigned int i = n - 1, j;

    /* étape 1: cherche i le plus grand tq P[i]<P[i+1] */
    j = n - 1;

    /*@ loop invariant 0 <= j <= n - 1;
        loop invariant \forall integer k ; j < k <= n - 1 ==> P[k - 1] >= P[k]; 
        loop assigns j;
        loop variant j;
    */
    while (j > 0 && P[j - 1] >= P[j])
    {
        j--;
    }
    //@ assert j == 0 || P[j-1] < P[j];


    /* on a trouvé un i tq P[i]<P[i+1] */
    if (j == 0)
    {
        return false; /* le plus grand i tq P[i]<[i+1] n'existe pas */
    }
    //@ assert j <= n - 1;
    //@ assert P[j-1] < P[j];


    i = j - 1;
    //@ assert i < n;
    //@ assert P[i] < P[i+1];

    /* étape 2: cherche j le plus grand tq P[i]<P[j] */

    /*@ loop invariant i < j <= n;
        loop invariant \forall integer k ; i < k < j  ==> P[i] < P[k]; 
        loop assigns j;
        loop variant n - j;
    */
    while ((j < n) && (P[i] < P[j]))
        j++;
    j--;
    
    swap(P + i, P + j);
    //@ assert isPermutation(P,n);
    reverse(P, i + 1, n - 1, n);
    //@ assert unchangedTab{Pre,Here}(P,P,0,i);
    //@ assert P[i] > \at(P[\at(i,Here)],Pre);
    return true;
}