#include <iostream>
#include <fstream>
#include <thread>
#include <ctime>
#include <iomanip>

using namespace std;

static const int NUM_TESTS = 1000;
static const int NUM_THREADS = 2;

void simpleThread() {
    for (int j = 0; j < 1000; j++) {
        this_thread::yield();
    }
}

int main() {
    double finalAverage = 0.0;

    ofstream outputFile("D:\\Coding\\SCSProject\\C++\\ThreadContextSwitch\\results.csv");

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
        }

        for (int j = 0; j < NUM_THREADS; j++) {
            threads[j].join();
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
