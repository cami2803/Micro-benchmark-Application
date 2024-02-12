#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define n 1000000
#define numAllocations 10000
#define numTests 10
#define FILE_PATH "D:\\Coding\\SCSProject\\C\\MemAlloc\\results.csv"

int main() {
    double average = 0.0;
    FILE* outputFile = fopen(FILE_PATH, "w");

    if (outputFile == NULL) {
        perror("Error opening file!");
        return 1;
    }

    fprintf(outputFile, "Test,Time (s)\n");

    for (int test = 0; test < numTests; test++) {
        clock_t startTime = clock();
        for (int i = 0; i < numAllocations; i++) {
            int* memoryBlock = (int*)malloc(n * sizeof(int));
            free(memoryBlock);
        }
        clock_t endTime = clock();
        double finalTimeSec = (double)(endTime - startTime) / CLOCKS_PER_SEC;
        average += finalTimeSec;

        fprintf(outputFile, "%d,%.2f\n", test + 1, finalTimeSec);
    }
    double finalTime = average / 10;
    printf("%.2f s\n", finalTime);
    fclose(outputFile);


    return 0;
}
