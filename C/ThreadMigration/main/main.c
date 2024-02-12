#include <stdio.h>
#include <pthread.h>
#include <time.h>
#include <math.h>

#define NUM_THREADS 2
#define NUM_ITERATIONS 100000

void* taskFunction(void* arg) {
    for (int i = 0; i < NUM_ITERATIONS; i++) {
        double result = sqrt((double)i);
    }
    return NULL;
}

int main() {
    FILE* csvFile = fopen("D:\\Coding\\SCSProject\\C\\ThreadMigration\\main\\results.csv", "w");
    double finalAverage = 0.0;
    if (csvFile == NULL) {
        perror("Error opening file");
        return 1;
    }

    fprintf(csvFile, "Test,Time\n");

    for (int test = 0; test < 10000; test++) {
        clock_t startTime = clock();

        pthread_t threads[NUM_THREADS];
        for (int j = 0; j < NUM_THREADS; j++) {
            pthread_create(&threads[j], NULL, taskFunction, NULL);
        }

        for (int j = 0; j < NUM_THREADS; j++) {
            pthread_join(threads[j], NULL);
        }

        clock_t endTime = clock();
        double timeSeconds = (double)(endTime - startTime) / CLOCKS_PER_SEC;
        finalAverage += timeSeconds;
        fprintf(csvFile, "%d,%.4f\n", test + 1, timeSeconds);
    }

    double finalTime = finalAverage / 10000;
    printf("%.4f s\n", finalTime);
    fclose(csvFile);

    return 0;
}
