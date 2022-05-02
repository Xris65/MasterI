#include "formalism.h"
#include "limits.h"

/*@ requires 0 < n < INT_MAX;
    requires \valid (P+(0..n-1));
    requires isPermutation(\at(P, Pre), n);

    terminates \true;

    ensures isPermutation(\at(P, Post), n);
    assigns P[0..n-1];

    behavior MaxPerm:
        assumes isMaxPerm{Pre}(P,n);
        ensures \result == false;
        ensures unchangedTab{Pre, Post}(P, P, 0, n);
    behavior NotMaxPerm:
        assumes !isMaxPerm{Pre}(P, n);
        ensures \result == true;
        ensures isStrictlyBiggerPerm{Pre, Post}(P, P, n);
    
    complete behaviors;
    disjoint behaviors MaxPerm,NotMaxPerm;

*/
bool NextPermutation(int *P, unsigned int n);