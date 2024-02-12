import java.text.DecimalFormat;
import java.io.FileWriter;
import java.io.IOException;

public class MemAllocStatic {
    static int[] staticMemoryBlock = new int[1000000];
    private static final DecimalFormat df = new DecimalFormat("0.0000");

    public static void main(String[] args) {

        double finalAverage = 0.0;

        try (FileWriter csvWriter = new FileWriter("D:\\Coding\\SCSProject\\Java\\Testing\\src\\resultsStatic.csv")) {
            csvWriter.append("Test,Time (s)\n");
            for (int i = 0; i < 100; i++) {
                long startTime = System.currentTimeMillis();
                int n = 1000000;
                for (int j = 0; j < 100000; j++) {
                    staticMemoryBlock[j % n] = j;
                }
                long endTime = System.currentTimeMillis();
                double finalTimeSec = (endTime - startTime) / 1000.0;
                finalAverage += finalTimeSec;

                csvWriter.append(String.format("%d,%s\n", i + 1, df.format(finalTimeSec)));
            }

            double finalTime = finalAverage / 100;
            System.out.println(df.format(finalTime) + " s");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
