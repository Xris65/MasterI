#include "reverse.h"

void reverse(int *t, unsigned int i, unsigned int j, unsigned int n)
{
    /*@ loop invariant I1 : 0 <= \at(i, Pre) <= i <= j + 1 <= \at(j, Pre) + 1 <= n;
        loop invariant I2 : i ==  \at(i, Pre) + \at(j, Pre) -j;
        loop invariant I3 : \forall integer k; i <= k <= j ==> t[k] == \at(t[k], Pre);
        loop invariant I4 : \forall integer k; (\at(i, Pre) <= k < i) ==> t[k] == \at(t[j - k + i], Pre);
        loop invariant I5 : \forall integer k; j < k <= \at(j, Pre) ==> t[k] == \at(t[j - k + i], Pre);
        loop assigns i,j,t[ \at(i,Pre)..\at(j,Pre )];
        loop variant j - i + 1;
    */

    while (i < j)
    {
        int aux = t[i];
        t[i] = t[j];
        t[j] = aux;
        i++;
        j--;
    }
}

/*@ decreases j;
*/
void reverseRec(int *t, unsigned int i, unsigned int j, unsigned int n) {
    if (i < j) {
        int aux = t[i];
        t[i] = t[j];
        t[j] = aux;
        i++;
        j--;
        // Le if est necessaire pour Frama-C, sinon il ne valide pas le require 0 <= i <= j < n
        if(i < j) // Ce test sera pourtant effectue au prochain appel recursif de la fonction
            reverseRec(t, i, j, n); 
    }
}