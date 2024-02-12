#include <stdio.h>
#include <pthread.h>
#include <time.h>

#define NUM_THREADS 2
#define NUM_ITERATIONS 1000
#define NUM_TESTS 1000
#define FILE_PATH "D:\\Coding\\SCSProject\\C\\ThreadContextSwitch\\main\\results.csv"

void* threadFunction(void* arg) {
    for (int i = 0; i < NUM_ITERATIONS; i++) {
        sched_yield();
    }
    return NULL;
}

int main() {
    double finalAverage = 0.0;
    FILE* outputFile = fopen(FILE_PATH, "w");

    if (outputFile == NULL) {
        perror("Error opening file!");
        return 1;
    }

    fprintf(outputFile, "Test,Time (s)\n");

    for (int test = 0; test < NUM_TESTS; test++) {
        clock_t startTime = clock();

        pthread_t threads[NUM_THREADS];
        for (int j = 0; j < NUM_THREADS; j++) {
            pthread_create(&threads[j], NULL, threadFunction, NULL);
        }

        for (int j = 0; j < NUM_THREADS; j++) {
            pthread_join(threads[j], NULL);
        }

        clock_t endTime = clock();
        double timeSeconds = (double)(endTime - startTime) / CLOCKS_PER_SEC;
        finalAverage += timeSeconds;

        fprintf(outputFile, "%d,%.4f\n", test + 1, timeSeconds);
    }

    double finalTime = finalAverage / NUM_TESTS;
    printf("%.4f s\n", finalTime);

    fclose(outputFile);

    return 0;
}
