/*@ requires 0 <= i <= j < n;
    requires \valid(t + (0..n-1));

    terminates \true;

    ensures \forall integer k; i <= k <= j ==> t[k] == \old(t[j-k+i]);
    ensures t == \old(t);
    assigns t[i..j];
*/
void reverse(int *t, unsigned int i, unsigned int j, unsigned int n);

/*@ requires 0 <= i <= j < n;
    requires \valid(t + (0..n-1));

    terminates \true;

    ensures \forall integer k; i <= k <= j ==> t[k] == \old(t[j-k+i]);
    ensures t == \old(t);
    assigns t[i..j];
*/
void reverseRec(int *t, unsigned int i, unsigned int j, unsigned int n);