import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class MemAllocDynamic {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {

        int n = 1000000;
        int numTests = 10;
        double finalAverage = 0.0;

        try (FileWriter csvWriter = new FileWriter("D:\\Coding\\SCSProject\\Java\\Testing\\src\\resultsDynamic.csv")) {
            csvWriter.append("Test,Time (s)\n");

            for (int i = 0; i < numTests; i++) {
                long startTime = System.currentTimeMillis();

                for (int j = 0; j < 10000; j++) {
                    int[] memoryBlock = new int[n];
                }

                long endTime = System.currentTimeMillis();
                double finalTimeSec = (endTime - startTime) / 1000.0;
                finalAverage += finalTimeSec;

                csvWriter.append(String.format("%d,%s\n", i + 1, df.format(finalTimeSec)));
            }

            double finalTime = finalAverage / numTests;
            System.out.println(df.format(finalTime) + " s");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
