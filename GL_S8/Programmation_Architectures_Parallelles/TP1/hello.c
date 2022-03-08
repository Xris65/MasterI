#include <stdio.h>
#include <omp.h>

int main()
{

#pragma omp parallel
#pragma omp omp_get_thread_num
{
  printf("Bonjour depuis thread numero %d!\n", omp_get_thread_num());
  #pragma omp barrier
  printf("Au revoir depuis thread numero %d!\n", omp_get_thread_num());
}

  return 0;
}
