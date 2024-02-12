using System;
using System.Diagnostics;
using System.IO;
using System.Threading;

public class Threading
{
    private const int ThreadsNumber = 50;
    private static readonly string FilePath = "D:\\Coding\\SCSProject\\C#\\Threading\\results.csv";

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
                Thread[] threads = new Thread[ThreadsNumber];

                for (int j = 0; j < ThreadsNumber; j++)
                {
                    threads[j] = new Thread(SimpleThread);
                    threads[j].Start();
                }

                foreach (Thread thread in threads)
                {
                    try
                    {
                        thread.Join();
                    }
                    catch (ThreadInterruptedException e)
                    {
                        Console.WriteLine(e.StackTrace);
                    }
                }

                stopwatch.Stop();

                double finalTimeSec = stopwatch.Elapsed.TotalSeconds;
                finalAverage += finalTimeSec;

                csvWriter.WriteLine($"{i + 1},{finalTimeSec:F3}");
            }
        }

        double finalTime = finalAverage / numTests;
        Console.WriteLine($"{finalTime:F3} s");
    }

    static void SimpleThread()
    {
        int a = 1;
        for (int i = 0; i < 10000; ++i)
        {
            a += i;
        }
    }
}
