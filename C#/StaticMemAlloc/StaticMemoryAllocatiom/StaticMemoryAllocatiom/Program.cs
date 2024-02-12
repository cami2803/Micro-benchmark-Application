using System;
using System.Diagnostics;
using System.IO;

public class MemAllocStatic
{
    private static int[] staticMemoryBlock = new int[1000000];
    private static readonly string FilePath = "D:\\Coding\\SCSProject\\C#\\StaticMemAlloc\\results.csv";

    public static void Main(string[] args)
    {
        int numTests = 100;
        double finalAverage = 0.0;

        using (StreamWriter csvWriter = new StreamWriter(FilePath))
        {
            csvWriter.WriteLine("Test,Time (s)");

            for (int i = 0; i < numTests; i++)
            {
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.Start();

                int n = 1000000;
                for (int j = 0; j < 100000; j++)
                {
                    staticMemoryBlock[j % n] = j;
                }

                stopwatch.Stop();

                double finalTimeSec = stopwatch.Elapsed.TotalSeconds;
                finalAverage += finalTimeSec;

                csvWriter.WriteLine($"{i + 1},{finalTimeSec:F4}");
            }
        }


        double finalTime = finalAverage / numTests;
        Console.WriteLine($"{finalTime:F4} s");
    }
}
