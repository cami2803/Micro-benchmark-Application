#include <iostream>
#include <fstream>
#include <thread>
#include <vector>
#include <ctime>
#include <iomanip>

using namespace std;

static const int threadsNumber = 50;
static const int iterations = 10000;
static const int NUM_TESTS = 1000;

void simpleThread() {
    int a = 1;
    for (int i = 0; i < iterations; ++i) {
        a += i;
    }
}

int main() {
    double finalAverage = 0.0;

    ofstream outputFile("D:\\Coding\\SCSProject\\C++\\Threading\\results.csv");

    if (!outputFile.is_open()) {
        cerr << "Error opening file!" << endl;
        return 1;
    }

    outputFile << "Test,Time (s)" << endl;

    for (int i = 0; i < NUM_TESTS; i++) {
        clock_t startTime = clock();

        vector<thread> threads;
        for (int j = 0; j < threadsNumber; j++) {
            threads.emplace_back(simpleThread);
        }

        for (auto &thread : threads) {
            thread.join();
        }

        clock_t endTime = clock();
        double timeSec = static_cast<double>(endTime - startTime) / CLOCKS_PER_SEC;
        finalAverage += timeSec;

        outputFile << i + 1 << "," << fixed << setprecision(3) << timeSec << endl;
    }

    double finalTime = finalAverage / NUM_TESTS;
    cout << fixed << setprecision(3) << finalTime << " s" << endl;

    outputFile.close();

    return 0;
}
