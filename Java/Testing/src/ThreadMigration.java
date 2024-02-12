import java.text.DecimalFormat;
import java.io.FileWriter;
import java.io.IOException;

public class ThreadMigration {
    private static final DecimalFormat df = new DecimalFormat("0.0000");
    public static void main(String[] args) {
        double finalAverage = 0.0;

        try (FileWriter csvWriter = new FileWriter("D:\\Coding\\SCSProject\\Java\\Testing\\src\\resultsMigration.csv")) {
            csvWriter.append("Test,Time (s)\n");
        for (int i = 0; i < 10000; i++)
        {
            long startTime = System.currentTimeMillis();

            Thread[] threads = new Thread[2];
            for (int j = 0; j < 2; j++) {
                threads[j] = new Thread(new Task("Thread " + (j + 1)));
                threads[j].start();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            long endTime = System.currentTimeMillis();
            double timeSeconds = (endTime - startTime) / 1000.0;
            finalAverage += timeSeconds;

            csvWriter.append(String.format("%d,%s\n", i + 1, df.format(timeSeconds)));
        }


        double finalTime = finalAverage / 10000;
        System.out.println(df.format(finalTime) + " s");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Task implements Runnable {
        private String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                Math.sqrt(i);
            }
            //System.out.println("Thread '" + name + "' is running on CPU " + Thread.currentThread().getId());
        }
    }
}
