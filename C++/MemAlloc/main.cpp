#include <iostream>
#include <fstream>
#include <ctime>
#include <iomanip>

using namespace std;

int main() {
    const int n = 1000000;
    const int NUM_TESTS = 10;
    double finalAverage = 0.0;

    ofstream outputFile("D:\\Coding\\SCSProject\\C++\\MemAlloc\\results.csv");

    if (!outputFile.is_open()) {
        cerr << "Error opening file!" << endl;
        return 1;
    }

    outputFile << "Test,Time (s)" << endl;

    for (int i = 0; i < NUM_TESTS; i++) {
        clock_t startTime = clock();

        for (int j = 0; j < 10000; j++) {
            int* memoryBlock = new int[n];
            delete[] memoryBlock;
        }

        clock_t endTime = clock();
        double finalTimeSec = static_cast<double>(endTime - startTime) / CLOCKS_PER_SEC;

        outputFile << i + 1 << "," << fixed << setprecision(2) << finalTimeSec << endl;

        finalAverage += finalTimeSec;
    }

    double finalTime = finalAverage / NUM_TESTS;
    cout << fixed << setprecision(2) << finalTime << " s" << endl;

    outputFile.close();

    return 0;
}
