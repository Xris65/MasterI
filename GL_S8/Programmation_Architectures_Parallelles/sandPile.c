#include "easypap.h"

#include <omp.h>
#include <stdbool.h>
#include <sys/mman.h>
#include <unistd.h>
#include <immintrin.h>
#include "arch_flags.h"

typedef unsigned int TYPE;

static TYPE *TABLE = NULL;

static inline TYPE *atable_cell(TYPE *restrict i, int y, int x)
{
    return i + y * DIM + x;
}

#define atable(y, x) (*atable_cell(TABLE, (y), (x)))

static inline TYPE *table_cell(TYPE *restrict i, int step, int y, int x)
{
    return DIM * DIM * step + i + y * DIM + x;
}

#define table(step, y, x) (*table_cell(TABLE, (step), (y), (x)))

static int in = 0;
static int out = 1;

static inline void swap_tables()
{
    int tmp = in;
    in = out;
    out = tmp;
}

#define RGB(r, g, b) rgba(r, g, b, 0xFF)

static TYPE max_grains;

void asandPile_refresh_img()
{
    unsigned long int max = 0;
    for (int i = 1; i < DIM - 1; i++)
        for (int j = 1; j < DIM - 1; j++)
        {
            int g = table(in, i, j);
            int r, v, b;
            r = v = b = 0;
            if (g == 1)
                v = 255;
            else if (g == 2)
                b = 255;
            else if (g == 3)
                r = 255;
            else if (g == 4)
                r = v = b = 255;
            else if (g > 4)
                r = b = 255 - (240 * ((double)g) / (double)max_grains);

            cur_img(i, j) = RGB(r, v, b);
            if (g > max)
                max = g;
        }
    max_grains = max;
}

/////////////////////////////  Initial Configurations

static inline void set_cell(int y, int x, unsigned v)
{
    atable(y, x) = v;
    if (opencl_used)
        cur_img(y, x) = v;
}

void asandPile_draw_4partout(void);

void asandPile_draw(char *param)
{
    // Call function ${kernel}_draw_${param}, or default function (second
    // parameter) if symbol not found
    hooks_draw_helper(param, asandPile_draw_4partout);
}

void ssandPile_draw(char *param)
{
    hooks_draw_helper(param, asandPile_draw_4partout);
}

void asandPile_draw_4partout(void)
{
    max_grains = 8;
    for (int i = 1; i < DIM - 1; i++)
        for (int j = 1; j < DIM - 1; j++)
            set_cell(i, j, 4);
}

void asandPile_draw_DIM(void)
{
    max_grains = DIM;
    for (int i = DIM / 4; i < DIM - 1; i += DIM / 4)
        for (int j = DIM / 4; j < DIM - 1; j += DIM / 4)
            set_cell(i, j, i * j / 4);
}

void asandPile_draw_alea(void)
{
    max_grains = 5000;
    for (int i = 0; i < DIM >> 3; i++)
    {
        set_cell(1 + random() % (DIM - 2), 1 + random() % (DIM - 2), 1000 + (random() % (4000)));
    }
}

void asandPile_draw_big(void)
{
    const int i = DIM / 2;
    set_cell(i, i, 100000);
}

static void one_spiral(int x, int y, int step, int turns)
{
    int i = x, j = y, t;

    for (t = 1; t <= turns; t++)
    {
        for (; i < x + t * step; i++)
            set_cell(i, j, 3);
        for (; j < y + t * step + 1; j++)
            set_cell(i, j, 3);
        for (; i > x - t * step - 1; i--)
            set_cell(i, j, 3);
        for (; j > y - t * step - 1; j--)
            set_cell(i, j, 3);
    }
    set_cell(i, j, 4);

    for (int i = -2; i < 3; i++)
        for (int j = -2; j < 3; j++)
            set_cell(i + x, j + y, 3);
}

static void many_spirals(int xdebut, int xfin, int ydebut, int yfin, int step,
                         int turns)
{
    int i, j;
    int size = turns * step + 2;

    for (i = xdebut + size; i < xfin - size; i += 2 * size)
        for (j = ydebut + size; j < yfin - size; j += 2 * size)
            one_spiral(i, j, step, turns);
}

static void spiral(unsigned twists)
{
    many_spirals(1, DIM - 2, 1, DIM - 2, 2, twists);
}

void asandPile_draw_spirals(void)
{
    spiral(DIM / 32);
}

// shared functions

#define ALIAS(fun)         \
    void ssandPile_##fun() \
    {                      \
        asandPile_##fun(); \
    }

ALIAS(refresh_img);
ALIAS(draw_4partout);
ALIAS(draw_DIM);
ALIAS(draw_alea);
ALIAS(draw_big);
ALIAS(draw_spirals);

//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
///////////////////////////// Synchronous Kernel
//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////

void ssandPile_init()
{
    TABLE = calloc(2 * DIM * DIM, sizeof(TYPE));
}

void ssandPile_finalize()
{
    free(TABLE);
}

int ssandPile_do_tile_default(int x, int y, int width, int height)
{
    int diff = 0;

    for (int i = y; i < y + height; i++)
        for (int j = x; j < x + width; j++)
        {
            table(out, i, j) = table(in, i, j) % 4;
            table(out, i, j) += table(in, i + 1, j) / 4;
            table(out, i, j) += table(in, i - 1, j) / 4;
            table(out, i, j) += table(in, i, j + 1) / 4;
            table(out, i, j) += table(in, i, j - 1) / 4;
            if (table(out, i, j) >= 4)
                diff = 1;
        }

    return diff;
}

//

int ssandPile_do_tile_opt(int x, int y, int width, int height)
{
    int diff = 0;

    for (int i = y; i < y + height; i++)
        for (int j = x; j < x + width; j++)
        {
            table(out, i, j) = table(in, i, j) % 4 + table(in, i + 1, j) / 4 +
                               table(in, i - 1, j) / 4 + table(in, i, j + 1) / 4 +
                               table(in, i, j - 1) / 4;
            diff += table(out, i, j) >= 4;
        }

    return diff;
}

//

unsigned ssandPile_do_tile_avx(int x, int y, int width, int height)
{
    if (x == 1 || y == 1 || x + width == DIM - 1 || y + height == DIM - 1)
        return ssandPile_do_tile_opt(x, y, width, height);

    unsigned diff = 0;
    __m512i set3 = _mm512_set1_epi32(3);
    for (int i = y; i < y + height; i++)
    {
        for (int j = x; j < x + width; j += AVX512_VEC_SIZE_INT)
        {
            // optimal version using AVX
            __m512i base_table = _mm512_loadu_epi32(&table(in, i, j));

            base_table = _mm512_and_epi32(base_table, set3);
            __m512i right = _mm512_loadu_epi32(&table(in, i, j + 1));
            base_table = _mm512_add_epi32(base_table, _mm512_srli_epi32(right, 2));
            __m512i left = _mm512_loadu_epi32(&table(in, i, j - 1));
            base_table = _mm512_add_epi32(base_table, _mm512_srli_epi32(left, 2));
            __m512i down = _mm512_loadu_epi32(&table(in, i + 1, j));
            base_table = _mm512_add_epi32(base_table, _mm512_srli_epi32(down, 2));
            __m512i up = _mm512_loadu_epi32(&table(in, i - 1, j));
            base_table = _mm512_add_epi32(base_table, _mm512_srli_epi32(up, 2));
            _mm512_storeu_epi32(&table(out, i, j), base_table);

            diff += _mm512_cmpgt_epi32_mask(base_table, set3);
        }
    }
    return diff;
}

/* unsigned ssandPile_do_tile_avx2(int x, int y, int width, int height)
{
  unsigned diff = 0;
  __m512i set3 = _mm512_set1_epi32(3);
    for(int i = y; i < y + height; i++)
    {
      for(int j = x; j < x + width; j+=AVX512_VEC_SIZE_INT)
      {
        // optimal version using AVX
        __m512i base_table = _mm512_loadu_epi32(&table(in, i, j));

        base_table = _mm512_and_epi32(base_table, set3);
        if(j  != DIM - width - 1)
        {
        __m512i right = _mm512_loadu_epi32(&table(in, i, j + 1));
        base_table = _mm512_add_epi32(base_table, _mm512_srli_epi32(right, 2));
        }
        else {
          __m512i right = _mm512_alignr_epi32(base_table, _mm512_setzero_si512() , AVX512_VEC_SIZE_INT - 1);
          base_table = _mm512_add_epi32(base_table, _mm512_srli_epi32(right, 2));
        }
        if(j!=1)
        {
          __m512i left = _mm512_loadu_epi32(&table(in, i, j - 1 ));
          base_table = _mm512_add_epi32(base_table, _mm512_srli_epi32(left, 2));
        }
        else{
          __m512i left = _mm512_alignr_epi32( _mm512_setzero_si512() , base_table ,1);
          base_table = _mm512_add_epi32(base_table, _mm512_srli_epi32(left, 2));
        }
        __m512i down = _mm512_loadu_epi32(&table(in, i + 1, j));
        base_table = _mm512_add_epi32(base_table, _mm512_srli_epi32(down, 2));
        __m512i up = _mm512_loadu_epi32(&table(in, i - 1, j));
        base_table = _mm512_add_epi32(base_table, _mm512_srli_epi32(up, 2));
        _mm512_storeu_epi32(&table(out, i, j), base_table);

        diff += _mm512_cmpgt_epi32_mask(base_table, set3);
      }
    }
  return diff;

} */

// Renvoie le nombre d'itérations effectuées avant stabilisation, ou 0
unsigned ssandPile_compute_seq(unsigned nb_iter)
{
    for (unsigned it = 1; it <= nb_iter; it++)
    {
        int change = do_tile(1, 1, DIM - 2, DIM - 2, 0);
        swap_tables();
        if (change == 0)
            return it;
    }
    return 0;
}

unsigned ssandPile_compute_tiled(unsigned nb_iter)
{
    for (unsigned it = 1; it <= nb_iter; it++)
    {
        int change = 0;

        for (int y = 0; y < DIM; y += TILE_H)
            for (int x = 0; x < DIM; x += TILE_W)
                change |=
                    do_tile(x + (x == 0), y + (y == 0),
                            TILE_W - ((x + TILE_W == DIM) + (x == 0)),
                            TILE_H - ((y + TILE_H == DIM) + (y == 0)), 0 /* CPU id */);
        swap_tables();
        if (change == 0)
            return it;
    }

    return 0;
}

//
unsigned ssandPile_compute_omp(unsigned nb_iter)
{
    unsigned res = 0;
#pragma omp parallel for schedule(runtime)
    for (unsigned it = 1; it <= nb_iter; it++)
    {
        int change = do_tile(1, 1, DIM - 2, DIM - 2, omp_get_thread_num());
        swap_tables();
        if (change == 0)
        {
            res = it;
        }
    }
    return res;
}

unsigned ssandPile_compute_omp_tiled(unsigned nb_iter)
{
    for (unsigned it = 1; it <= nb_iter; it++)
    {
        int change = 0;
#pragma omp parallel for collapse(2) reduction(| \
                                               : change)
        for (int y = 0; y < DIM; y += TILE_H)
            for (int x = 0; x < DIM; x += TILE_W)
                change |=
                    do_tile(x + (x == 0), y + (y == 0),
                            TILE_W - ((x + TILE_W == DIM) + (x == 0)),
                            TILE_H - ((y + TILE_H == DIM) + (y == 0)), omp_get_thread_num());
        swap_tables();
        if (change == 0)
            return it;
    }

    return 0;
}

unsigned ssandPile_compute_omp_taskloop(unsigned nb_iter)
{

    for (unsigned it = 1; it <= nb_iter; it++)
    {
        int change = 0;
#pragma omp parallel reduction(| \
                               : change)
        {
#pragma omp single
            {
                for (int y = 0; y < DIM; y += TILE_H)
                    for (int x = 0; x < DIM; x += TILE_W)
#pragma omp task
                        change |=
                            do_tile(x + (x == 0), y + (y == 0),
                                    TILE_W - ((x + TILE_W == DIM) + (x == 0)),
                                    TILE_H - ((y + TILE_H == DIM) + (y == 0)), omp_get_thread_num());
            }
        }

        swap_tables();
        if (change == 0)
            return it;
    }

    return 0;
}

// Only called when --dump or --thumbnails is used
void ssandPile_refresh_img_ocl()
{
    cl_int err;

    err =
        clEnqueueReadBuffer(queue, cur_buffer, CL_TRUE, 0,
                            sizeof(unsigned) * DIM * DIM, TABLE, 0, NULL, NULL);
    check(err, "Failed to read buffer from GPU");

    ssandPile_refresh_img();
}

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////OCL Hybrid version/////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

#define THRESHOLD 10
#define GPU_TILE_NUM (DIM / GPU_TILE_W) * (DIM / GPU_TILE_H)

static unsigned cpu_y_part;
static unsigned gpu_y_part;
static cl_mem buffer;
static bool *gpu_change;
static bool gpu_changed;
static unsigned gpu_iterations = 0;

void ssandPile_init_ocl_hybrid(void)
{
    if (GPU_TILE_H != TILE_H)
        exit_with_error("CPU and GPU Tiles should have the same height (%d != %d)",
                        GPU_TILE_H, TILE_H);

    cpu_y_part = (NB_TILES_Y / 2) * GPU_TILE_H; // Start with fifty-fifty
    gpu_y_part = DIM - cpu_y_part;
    buffer = clCreateBuffer(context, CL_MEM_READ_WRITE,
                            sizeof(unsigned) * DIM * DIM, NULL, NULL);
    gpu_change = (bool *)calloc(GPU_TILE_NUM, sizeof(bool));

    ssandPile_init();
}

static long gpu_duration = 0, cpu_duration = 0;

static int much_greater_than(long t1, long t2)
{
    return (t1 > t2) && ((t1 - t2) * 100 / t1 > THRESHOLD);
}
void ssandPile_refresh_img_ocl_hybrid()
{
    cl_int err;

    err =
        clEnqueueReadBuffer(queue, cur_buffer, CL_TRUE, cpu_y_part * DIM * sizeof(unsigned),
                            (gpu_y_part - 1) * DIM * sizeof(unsigned), &table(in, cpu_y_part, 0), 0, NULL, NULL);
    check(err, "Failed to read buffer from GPU");

    ssandPile_refresh_img();
}

unsigned ssandPile_invoke_ocl_hybrid(unsigned nb_iter)
{
    size_t global[2] = {DIM,
                        gpu_y_part}; // global domain size for our calculation
    size_t local[2] = {GPU_TILE_W,
                       GPU_TILE_H}; // local domain size for our calculation
    cl_int err;

    cl_event kernel_event;
    long t1, t2;
    int gpu_accumulated_lines = 0;

    for (unsigned it = 1; it <= nb_iter; it++)
    {
        int change = 0;
        gpu_changed = false;
        gpu_iterations++;

        // Load balancing
         if (gpu_duration != 0)
        {
            if (much_greater_than(gpu_duration, cpu_duration) &&
                gpu_y_part > GPU_TILE_H)
            {
                gpu_y_part -= GPU_TILE_H;
                cpu_y_part += GPU_TILE_H;
                global[1] = gpu_y_part;
            }
            else if (much_greater_than(cpu_duration, gpu_duration) &&
                     cpu_y_part > GPU_TILE_H)
            {
                gpu_y_part += GPU_TILE_H;
                cpu_y_part -= GPU_TILE_H;
                global[1] = gpu_y_part;
            }
        } 

        // Set kernel arguments
        //
        err = 0;
        err |= clSetKernelArg(compute_kernel, 0, sizeof(cl_mem), &cur_buffer);
        err |= clSetKernelArg(compute_kernel, 1, sizeof(cl_mem), &next_buffer);
        err |= clSetKernelArg(compute_kernel, 2, sizeof(cl_mem), &buffer);
        err |= clSetKernelArg(compute_kernel, 3, sizeof(unsigned), &cpu_y_part);

        check(err, "Failed to set kernel arguments");

        // Launch GPU kernel
        err = clEnqueueNDRangeKernel(queue, compute_kernel, 2, NULL, global, local,
                                     0, NULL, &kernel_event);
        check(err, "Failed to execute kernel");
        clFlush(queue);

        t1 = what_time_is_it();
        // Compute CPU part
#pragma omp parallel for collapse(2) schedule(runtime) reduction(| \
                                                                 : change)
        for (int y = 0; y < cpu_y_part; y += TILE_H)
            for (int x = 0; x < DIM; x += TILE_W)
                change |= do_tile(x + (x == 0), y + (y == 0),
                                  TILE_W - ((x + TILE_W == DIM) + (x == 0)),
                                  TILE_H - ((y + TILE_H == DIM) + (y == 0)), omp_get_thread_num());

        err = clEnqueueReadBuffer(queue, next_buffer, CL_TRUE, cpu_y_part * DIM * sizeof(unsigned),
                                  DIM * sizeof(unsigned), &table(out, cpu_y_part, 0), 0,
                                  NULL, NULL);
        check(err, "Failed to read next buffer");
        err = clEnqueueWriteBuffer(queue, next_buffer, CL_TRUE, (cpu_y_part - 1) * DIM * sizeof(unsigned),
                                   DIM * sizeof(unsigned), &table(out, cpu_y_part - 1, 0), 0,
                                   NULL, NULL);
        check(err, "Failed to write to next buffer");
        err = clEnqueueReadBuffer(queue, buffer, CL_TRUE, 0,
                                  GPU_TILE_NUM * sizeof(bool), gpu_change, 0,
                                  NULL, NULL);
        check(err, "Failed to write to change buffer");
        // Swap buffers
        {
            cl_mem tmp = cur_buffer;
            cur_buffer = next_buffer;
            next_buffer = tmp;
        }
        swap_tables();
        t2 = what_time_is_it();
        cpu_duration = t2 - t1;

        clFinish(queue);

        gpu_duration = ocl_monitor(kernel_event, 0, cpu_y_part, global[0],
                                   global[1], TASK_TYPE_COMPUTE);
        clReleaseEvent(kernel_event);
        if (gpu_iterations / 100 != 0) // Pour ignorer les 100 premieres iterations
        {
            for (int tile_no = 0; tile_no < GPU_TILE_NUM; tile_no++)
                gpu_changed |= gpu_change[tile_no];
            if (!gpu_changed && !change)
            {
                err = clEnqueueWriteBuffer(queue, cur_buffer, CL_TRUE, 0,
                                           DIM * (cpu_y_part - 1) * sizeof(unsigned), &table(in, 0, 0), 0,
                                           NULL, NULL);
                return it;
            }
        }
        else
            gpu_changed = true;
        gpu_accumulated_lines += gpu_y_part;
        if (!gpu_changed && !change)
        {
            err = clEnqueueWriteBuffer(queue, cur_buffer, CL_TRUE, 0,
                                       DIM * (cpu_y_part - 1) * sizeof(unsigned), &table(in, 0, 0), 0,
                                       NULL, NULL);
            return it;
        }
    }

    if (do_display)
    {
        // Send CPU contribution to GPU memory
        err = clEnqueueWriteBuffer(queue, cur_buffer, CL_TRUE, 0,
                                   DIM * (cpu_y_part - 1) * sizeof(unsigned), &table(in, 0, 0), 0,
                                   NULL, NULL);
        check(err, "Failed to write to buffer");
    }
    else
        PRINT_DEBUG('u', "In average, GPU took %.1f%% of the lines\n",
                    (float)gpu_accumulated_lines * 100 / (DIM * nb_iter));

    return 0;
}

//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
///////////////////////////// Asynchronous Kernel
//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////

bool **stable1;
bool **stable2;

void asandPile_init()
{
    in = out = 0;
    if (TABLE == NULL)
    {
        const unsigned size = DIM * DIM * sizeof(TYPE);

        PRINT_DEBUG('u', "Memory footprint = 2 x %d bytes\n", size);

        TABLE = mmap(NULL, size, PROT_READ | PROT_WRITE,
                     MAP_PRIVATE | MAP_ANONYMOUS, -1, 0);
        stable1 = (bool **)malloc(sizeof(bool *) * DIM);
        stable2 = (bool **)malloc(sizeof(bool *) * DIM);
        for (int i = 0; i < DIM; i++)
        {
            stable1[i] = (bool *)malloc(sizeof(bool) * DIM);
            stable2[i] = (bool *)malloc(sizeof(bool) * DIM);
            for (int j = 0; j < DIM; j++)
            {
                stable1[i][j] = false;
                stable2[i][j] = false;
            }
        }
    }
}

void asandPile_finalize()
{
    const unsigned size = DIM * DIM * sizeof(TYPE);
    for (int i = 0; i < DIM; i++)
    {
        free(stable1[i]);
        free(stable2[i]);
    }
    free(stable1);
    free(stable2);
    munmap(TABLE, size);
}

///////////////////////////// Version séquentielle simple (seq)
// Renvoie le nombre d'itérations effectuées avant stabilisation, ou 0

int asandPile_do_tile_default(int x, int y, int width, int height)
{
    int change = 0;

    for (int i = y; i < y + height; i++)
        for (int j = x; j < x + width; j++)
            if (atable(i, j) >= 4)
            {
                atable(i, j - 1) += atable(i, j) / 4;
                atable(i, j + 1) += atable(i, j) / 4;
                atable(i - 1, j) += atable(i, j) / 4;
                atable(i + 1, j) += atable(i, j) / 4;
                atable(i, j) %= 4;
                change = 1;
            }
    return change;
}

//
int asandPile_do_tile_opt(int x, int y, int width, int height)
{
    int change = 0;

    for (int i = y; i < y + height; i++)
        for (int j = x; j < x + width; j++)
        {
            int atableValue = atable(i, j);
            if (atableValue >= 4)
            {
                atableValue /= 4;
                atable(i, j - 1) += atableValue;
                atable(i, j + 1) += atableValue;
                atable(i - 1, j) += atableValue;
                atable(i + 1, j) += atableValue;
                atable(i, j) %= 4;
                change = 1;
            }
        }
    return change;
}
//

int asandPile_do_tile_avx(int x, int y, int width, int height)
{
    if (x == 1 || y == 1 || x == DIM - width - 1 || y == DIM - height - 1)
    {
        return asandPile_do_tile_opt(x, y, width, height);
    }
    int change = 0;
    __m512i set3 = _mm512_set1_epi32(3);
    for (int i = y; i < y + height; i++)
        for (int j = x; j < x + width; j += AVX512_VEC_SIZE_INT)
        {
            int unstable = atable(i, j) >= 4;

            //  ------------ load
            __m512i up = _mm512_loadu_epi32(&atable(i - 1, j));
            __m512i base_table = _mm512_loadu_epi32(&atable(i, j));
            __m512i down = _mm512_loadu_epi32(&atable(i + 1, j));

            __mmask16 mask = 0xffff /* >> (j == DIM - AVX512_VEC_SIZE_INT) << (j == 0) */;

            // ------------ let the sand fall synchronously
            // D <- Tj,i div 4
            __m512i D = _mm512_srli_epi32(base_table, 2);
            __m512i mod4 = _mm512_and_epi32(base_table, set3);
            __m512i dright = _mm512_alignr_epi32(_mm512_setzero_si512(), D, 1);
            __m512i dleft = _mm512_alignr_epi32(D, _mm512_setzero_si512(), AVX512_VEC_SIZE_INT - 1);
            base_table = _mm512_add_epi32(mod4, dright);
            base_table = _mm512_add_epi32(base_table, dleft);
            up = _mm512_add_epi32(up, D);
            down = _mm512_add_epi32(down, D);
            // ------------ finally store
            // put D in a array
            TYPE save[AVX512_VEC_SIZE_INT];
            _mm512_storeu_epi32(&save, D);

            atable(i, j - 1) += save[0];
            atable(i, j + AVX512_VEC_SIZE_INT) += save[AVX512_VEC_SIZE_INT - 1];

            _mm512_storeu_epi32(&atable(i, j), _mm512_maskz_abs_epi32(mask, base_table));
            _mm512_storeu_epi32(&atable(i - 1, j), _mm512_maskz_abs_epi32(mask, up));
            _mm512_storeu_epi32(&atable(i + 1, j), _mm512_maskz_abs_epi32(mask, down));
            change += unstable;
        }
    return change;
}

//

unsigned asandPile_compute_seq(unsigned nb_iter)
{
    int change = 0;
    for (unsigned it = 1; it <= nb_iter; it++)
    {
        // On traite toute l'image en un coup (oui, c'est une grosse tuile)
        change = do_tile(1, 1, DIM - 2, DIM - 2, 0);

        if (change == 0)
            return it;
    }
    return 0;
}

unsigned asandPile_compute_tiled(unsigned nb_iter)
{
    for (unsigned it = 1; it <= nb_iter; it++)
    {
        int change = 0;

        for (int y = 0; y < DIM; y += TILE_H)
            for (int x = 0; x < DIM; x += TILE_W)
                change |=
                    do_tile(x + (x == 0), y + (y == 0),
                            TILE_W - ((x + TILE_W == DIM) + (x == 0)),
                            TILE_H - ((y + TILE_H == DIM) + (y == 0)), 0 /* CPU id */);
        if (change == 0)
            return it;
    }
    return 0;
}

unsigned asandPile_compute_omp(unsigned nb_iter)
{

    for (unsigned it = 1; it <= nb_iter; it++)
    {
        int change = 0;

#pragma omp parallel for schedule(runtime) collapse(2) reduction(| \
                                                                 : change)
        for (int y = TILE_H; y < DIM; y += TILE_H * 2)
            for (int x = TILE_W; x < DIM; x += TILE_W * 2)
                change |=
                    do_tile(x + (x == 0), y + (y == 0),
                            TILE_W - ((x + TILE_W == DIM) + (x == 0)),
                            TILE_H - ((y + TILE_H == DIM) + (y == 0)), omp_get_thread_num());

#pragma omp parallel for schedule(runtime) collapse(2) reduction(| \
                                                                 : change)
        for (int y = 0; y < DIM; y += TILE_H * 2)
            for (int x = 0; x < DIM; x += TILE_W * 2)
                change |=
                    do_tile(x + (x == 0), y + (y == 0),
                            TILE_W - ((x + TILE_W == DIM) + (x == 0)),
                            TILE_H - ((y + TILE_H == DIM) + (y == 0)), omp_get_thread_num());

#pragma omp parallel for schedule(runtime) collapse(2) reduction(| \
                                                                 : change)
        for (int y = 0; y < DIM; y += TILE_H * 2)
            for (int x = TILE_W; x < DIM; x += TILE_W * 2)
                change |=
                    do_tile(x + (x == 0), y + (y == 0),
                            TILE_W - ((x + TILE_W == DIM) + (x == 0)),
                            TILE_H - ((y + TILE_H == DIM) + (y == 0)), omp_get_thread_num());

#pragma omp parallel for schedule(runtime) collapse(2) reduction(| \
                                                                 : change)
        for (int y = TILE_H; y < DIM; y += TILE_H * 2)
            for (int x = 0; x < DIM; x += TILE_W * 2)
                change |=
                    do_tile(x + (x == 0), y + (y == 0),
                            TILE_W - ((x + TILE_W == DIM) + (x == 0)),
                            TILE_H - ((y + TILE_H == DIM) + (y == 0)), omp_get_thread_num());

        if (change == 0)
            return it;
    }
    return 0;
}

unsigned asandPile_compute_lazy(unsigned nb_iter)
{ // Version aussi rapide mais sans effectuer de calculs inutiles
    for (unsigned it = 1; it <= nb_iter; it++)
    {
        int change = 0;
        for (int y = 0; y < DIM; y += TILE_H)
        {
            for (int x = 0; x < DIM; x += TILE_W)
            {
                for (int i = y; (i < y + TILE_H) && i < DIM; i++)
                {
                    bool leave = false;
                    for (int j = x; (j < x + TILE_W) && j < DIM; j++)
                    {
                        if (atable(i, j) >= 4)
                        {
                            change |=
                                do_tile(x + (x == 0), y + (y == 0),
                                        TILE_W - ((x + TILE_W == DIM) + (x == 0)),
                                        TILE_H - ((y + TILE_H == DIM) + (y == 0)), 0);
                            leave = true;
                            break;
                        }
                    }
                    if (leave)
                        break;
                }
            }
        }

        if (change == 0)
            return it;
    }
    return 0;
}

bool isStable1(int y, int x)
{
    return stable1[y / TILE_H][x / TILE_W];
}

void setStable1(int y, int x, bool value)
{
    stable1[y / TILE_H][x / TILE_W] = value;
}

// return true if one neighbor is unstable1
bool check_unstable1_neighbors(int x, int y)
{
    if (!isStable1(y, x))
        return true;
    // top
    if (y != 0 && !isStable1(y - 1, x))
        return true;
    // bottom
    if (y != DIM - 1 && !isStable1(y + 1, x))
        return true;
    // left
    if (x != 0 && !isStable1(y, x - 1))
        return true;
    // right
    if (x != DIM - 1 && !isStable1(y, x + 1))
        return true;
    return false;
}

unsigned asandPile_compute_lazy2(unsigned nb_iter)
{
    for (unsigned it = 1; it <= nb_iter; it++)
    {
        int change = 0;

        for (int y = 0; y < DIM; y += TILE_H)
        {
            for (int x = 0; x < DIM; x += TILE_W)
            {
                bool calculated = check_unstable1_neighbors(x, y) &&
                                  do_tile(x + (x == 0), y + (y == 0),
                                          TILE_W - ((x + TILE_W == DIM) + (x == 0)),
                                          TILE_H - ((y + TILE_H == DIM) + (y == 0)), 0 /* CPU id */);
                change |= calculated;
                setStable1(y, x, !calculated);
            }
        }
        if (change == 0)
            return it;
    }
    return 0;
}
