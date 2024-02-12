using System;
using System.Diagnostics;
using System.IO;
using System.Threading;

public class ThreadContextSwitch
{
    private static readonly string FilePath = "D:\\Coding\\SCSProject\\C#\\ThreadContextSwitch\\results.csv";

    public static void Main(string[] args)
    {
        int numTests = 1000;
        double finalAverage = 0.0;

        using (StreamWriter csvWriter = new StreamWriter(FilePath))
        {
            csvWriter.WriteLine("Test,Time (s)");

            for (int i = 0; i < numTests; i++)
            {
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.Start();

                Thread thread1 = new Thread(() =>
                {
                    for (int j = 0; j < 1000; j++)
                    {
                        Thread.Yield();
                    }
                });

                Thread thread2 = new Thread(() =>
                {
                    for (int j = 0; j < 1000; j++)
                    {
                        Thread.Yield();
                    }
                });

                thread1.Start();
                thread2.Start();

                try
                {
                    thread1.Join();
                    thread2.Join();
                }
                catch (ThreadInterruptedException e)
                {
                    Console.WriteLine(e.StackTrace);
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
