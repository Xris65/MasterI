#include <stdlib.h>
#include <stdio.h>

#include <omp.h>

int 
main()
{
  int i;
  #pragma omp parallel
  #pragma omp for schedule(guided,3)
  for(i=0; i < 50; i++){
    printf("%d traite %i\n",omp_get_thread_num(),i);
  }
  return 0;
}
