#include "permutations.h"

void initPerm(int *P, unsigned int n)
{   
    int i = 0;
    /*@
        loop invariant 0 <= i <=n;
        loop invariant \forall integer k; 0 <=k < i ==> P[k]==k;
        
        loop assigns P[0..n - 1],i;
        loop variant n - i;
    */
    while(i < n){
        P[i] = i;
        i++;
    }
    return;
}

bool isMaxPerm(int *P, unsigned int n)
{   
    if (n == 0) // Le if est necessaire pour Frama-C, sinon il ne valide presque rien..
        return true;
    int i = 0;
    /*@
        loop invariant 0 <= i < n;
        loop invariant \forall integer k; 0 <= k < i ==> P[k] > P[k+1];       
        loop assigns i;
        loop variant n - i - 1;
    */
    while(i < n - 1){
        if(P[i] <= P[i+1])
            return false;
        i++;
    }
    return true;
}