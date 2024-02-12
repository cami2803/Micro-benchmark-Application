#include <stdio.h>
#include <time.h>

#define ARRAY_SIZE 1000000
#define ITERATIONS 100000
#define NUM_TESTS 100
#define FILE_PATH "D:\\Coding\\SCSProject\\C\\MemAllocStatic\\results.csv"

int staticMemoryBlock[ARRAY_SIZE];

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

        int n = ARRAY_SIZE;
        for (int j = 0; j < ITERATIONS; j++) {
            staticMemoryBlock[j % n] = j;
        }

        clock_t endTime = clock();
        double finalTimeSec = ((double)(endTime - startTime)) / CLOCKS_PER_SEC;
        finalAverage += finalTimeSec;

        fprintf(outputFile, "%d,%.4f\n", test + 1, finalTimeSec);
    }

    double finalTime = finalAverage / NUM_TESTS;
    printf("%.4f s\n", finalTime);

    fclose(outputFile);

    return 0;
}
