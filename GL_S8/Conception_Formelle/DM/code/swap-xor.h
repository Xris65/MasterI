/*@ requires \valid(a);
    requires \valid(b);
    requires \separated(a, b);
    terminates \true;
    
    assigns *a, *b;
    ensures *a == \old(*b);
    ensures *b == \old(*a);
*/
void swap(int *a, int *b);