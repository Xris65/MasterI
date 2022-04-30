#include "behavioral_loop.h"

int behavioral_loop(int n, int c){
	int result = 0;
	/*@ loop invariant 1 <= i <= n + 1;
		loop invariant result <= 2 * (i -1);
		loop assigns result,i;

		for cPos :
	   		loop invariant result == 2 * (i -1);

	  	for cNeg: 
		  	loop invariant result == i - 1;
		
	*/
	for(int i = 1; i <= n; i++){
		if(c >= 0) result++;
		result++;
	}
	return result;
}