using System;
using System.Diagnostics;
using System.IO;
using System.Threading;

public class ThreadMigration
{
    private static readonly string FilePath = "D:\\Coding\\SCSProject\\C#\\ThreadMigration\\results.csv";

    public static void Main(string[] args)
    {
        int numTests = 10000;
        double finalAverage = 0.0;

        using (StreamWriter csvWriter = new StreamWriter(FilePath))
        {
            csvWriter.WriteLine("Test,Time (s)");

            for (int i = 0; i < numTests; i++)
            {
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.Start();

                Thread[] threads = new Thread[2];
                for (int j = 0; j < 2; j++)
                {
                    threads[j] = new Thread(new Task().Run);
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
                csvWriter.WriteLine($"{i + 1},{finalTimeSec:F4}");
            }
        }

        double finalTime = finalAverage / numTests;
        Console.WriteLine($"{finalTime:F4} s");
    }

    class Task
    {
        public void Run()
        {
            for (int i = 0; i < 100000; i++)
            {
                Math.Sqrt(i);
            }
        }
    }
}
