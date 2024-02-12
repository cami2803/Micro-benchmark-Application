using System;
using System.Diagnostics;
using System.IO;

class MemAllocDynamic
{
    static void Main()
    {
        int n = 1000000;
        int numTests = 10;
        double finalAverage = 0.0;
        string filePath = "D:\\Coding\\SCSProject\\C#\\MemAlloc\\results.csv";

        using (StreamWriter csvWriter = new StreamWriter(filePath))
        {
            csvWriter.WriteLine("Test,Time (s)");

            for (int i = 0; i < numTests; i++)
            {
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.Start();

                for (int j = 0; j < 10000; j++)
                {
                    int[] memoryBlock = new int[n];
                }

                stopwatch.Stop();

                double finalTimeSec = stopwatch.Elapsed.TotalSeconds;
                finalAverage += finalTimeSec;

                csvWriter.WriteLine($"{i + 1},{finalTimeSec:F2}");
            }
        }

        double finalTime = finalAverage / numTests;
        Console.WriteLine($"{finalTime:F2} s");
    }
}
