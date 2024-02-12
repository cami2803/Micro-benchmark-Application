#include <iostream>
#include <math.h>
#include <thread>
#include <fstream>
#include <ctime>
#include <iomanip>

using namespace std;

static const int NUM_TESTS = 10000;
static const int NUM_THREADS = 2;

void simpleThread() {
    for (int i = 0; i < 100000; i++) {
        sqrt(i);
    }
}

int main() {
    double finalAverage = 0.0;

    ofstream outputFile("D:\\Coding\\SCSProject\\C++\\ThreadMigration\\results.csv");

    if (!outputFile.is_open()) {
        cerr << "Error opening file!" << endl;
        return 1;
    }

    outputFile << "Test,Time (s)" << endl;

    for (int i = 0; i < NUM_TESTS; i++) {
        clock_t startTime = clock();

        thread threads[NUM_THREADS];
        for (int j = 0; j < NUM_THREADS; j++) {
            threads[j] = thread(simpleThread);
            threads[j].detach(); // Detach threads to allow them to run independently
        }

        clock_t endTime = clock();
        double timeSeconds = static_cast<double>(endTime - startTime) / CLOCKS_PER_SEC;
        finalAverage += timeSeconds;

        outputFile << i + 1 << "," << fixed << setprecision(4) << timeSeconds << endl;
    }

    double finalTime = finalAverage / NUM_TESTS;
    cout << fixed << setprecision(4) << finalTime << " s" << endl;

    outputFile.close();

    return 0;
}
