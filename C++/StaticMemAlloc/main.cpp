#include <iostream>
#include <fstream>
#include <ctime>
#include <iomanip>

using namespace std;

static const int arraySize = 1000000;
static const int iterations = 100000;
static const int NUM_TESTS = 100;

int staticMemoryBlock[arraySize];

int main() {
    ofstream outputFile("D:\\Coding\\SCSProject\\C++\\StaticMemAlloc\\results.csv");  // Open a CSV file for writing
    double finalAverage = 0.0;

    if (!outputFile.is_open()) {
        cerr << "Error opening file!" << endl;
        return 1;
    }

    outputFile << "Test,Time (s)" << endl;

    for (int i = 0; i < NUM_TESTS; i++) {
        clock_t startTime = clock();

        int n = arraySize;
        for (int j = 0; j < iterations; j++) {
            staticMemoryBlock[j % n] = j;
        }

        clock_t endTime = clock();
        double finalTimeSec = static_cast<double>(endTime - startTime) / CLOCKS_PER_SEC;

        outputFile << i + 1 << "," << fixed << setprecision(4) << finalTimeSec << endl;
        finalAverage += finalTimeSec;
    }

    double finalTime = finalAverage / NUM_TESTS;
    cout << fixed << setprecision(4) << finalTime << " s" << endl;
    outputFile.close();  // Close the file

    return 0;
}
