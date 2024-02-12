import java.text.DecimalFormat;
import java.io.FileWriter;
import java.io.IOException;

public class ThreadContextSwitch {

    private static final DecimalFormat df = new DecimalFormat("0.0000");

    public static void main(String[] args) {

        double finalAverage = 0.0;

        try (FileWriter csvWriter = new FileWriter("D:\\Coding\\SCSProject\\Java\\Testing\\src\\resultsContext.csv")) {
            csvWriter.append("Test,Time (s)\n");
            for (int i = 0; i < 1000; i++) {
                long startTimeMillis = System.currentTimeMillis();

                Thread thread = new Thread(() -> {
                    for (int j = 0; j < 1000; j++) {
                        Thread.yield();
                    }
                });

                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                long endTimeMillis = System.currentTimeMillis();
                double timeSeconds = (endTimeMillis - startTimeMillis) / 1000.0;
                finalAverage += timeSeconds;

                csvWriter.append(String.format("%d,%s\n", i + 1, df.format(timeSeconds).replace(',', '.')));
            }

            double finalTime = finalAverage / 1000;
            System.out.println(df.format(finalTime) + " s");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
