import java.text.DecimalFormat;
import java.io.FileWriter;
import java.io.IOException;

public class Threading {
    static final int threadsNumber = 50;
    private static final DecimalFormat df = new DecimalFormat("0.000");

    public static void main(String[] args) {

        double finalAverage = 0.0;
        try (FileWriter csvWriter = new FileWriter("D:\\Coding\\SCSProject\\Java\\Testing\\src\\resultsThreading.csv")) {
            csvWriter.append("Test,Time (s)\n");
        for (int i = 0; i < 1000; i++)
        {
            long startTimeMillis = System.currentTimeMillis();
            Thread[] threads = new Thread[threadsNumber];
            for (int j = 0; j < threadsNumber; j++) {
                threads[j] = new SimpleThread();
                threads[j].start();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            long endTimeMillis = System.currentTimeMillis();
            double timeSec = (endTimeMillis - startTimeMillis) / 1000.0;
            finalAverage += timeSec;

            csvWriter.append(String.format("%d,%s\n", i + 1, df.format(timeSec)));
        }

        double finalTime = finalAverage / 1000;
        System.out.println(df.format(finalTime) + " s");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class SimpleThread extends Thread {
        public void run() {
            int a = 1;
            for (int i = 0; i < 10000; ++i) {
                a += i;
            }
        }
    }
}
