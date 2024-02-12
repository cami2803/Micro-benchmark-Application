#include <stdio.h>
#include <pthread.h>
#include <time.h>

#define NUM_THREADS 50
#define NUM_ITERATIONS 10000

void *simpleFunction(void *arg) {
    int a = 1;
    for (int i = 0; i < NUM_ITERATIONS; ++i) {
        a += i;
    }
    return NULL;
}

int main() {
    FILE *csvFile = fopen("D:\\Coding\\SCSProject\\C\\Threading\\results.csv", "w");
    double averageTime = 0.0;
    if (csvFile == NULL) {
        perror("Error opening file");
        return 1;
    }

    fprintf(csvFile, "Test,Time\n");

    for (int test = 0; test < 1000; test++) {
        clock_t startTime = clock();

        pthread_t threads[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            pthread_create(&threads[i], NULL, simpleFunction, NULL);
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            pthread_join(threads[i], NULL);
        }

        clock_t endTime = clock();
        double time = (double)(endTime - startTime) / CLOCKS_PER_SEC;

        fprintf(csvFile, "%d,%.3f\n", test + 1, time);
        averageTime += time;
    }

    printf("%.3f s", averageTime/1000.0);
    fclose(csvFile);

    return 0;
}
