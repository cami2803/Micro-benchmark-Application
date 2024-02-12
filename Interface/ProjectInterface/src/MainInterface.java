import java.awt.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.LogarithmicAxis;

public class MainInterface {
    private JFrame frame;
    private JTable table;
    private JButton btnNewButton;
    private JPanel chartPanel;
    private String[][] benchmarkResults = new String[][]{
            {"Static Memory Allocation", null, null, null, null},
            {"Dynamic Memory Allocation", null, null, null, null},
            {"Thread Creation", null, null, null, null},
            {"Thread Context Switch", null, null, null, null},
            {"Thread Migration", null, null, null, null},
    };

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainInterface window = new MainInterface();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainInterface() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());
        frame.setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Performance Measurement for C, C++, C# and Java");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        btnNewButton = new JButton("Start benchmarking");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        contentPane.add(btnNewButton, BorderLayout.WEST);

        DefaultTableModel model = new DefaultTableModel(
                new Object[][] {
                        {"Static Memory Allocation", null, null, null, null},
                        {"Dynamic Memory Allocation", null, null, null, null},
                        {"Thread Creation", null, null, null, null},
                        {"Thread Context Switch", null, null, null, null},
                        {"Thread Migration", null, null, null, null},
                },
                new String[]{
                        "Task Selected", "C", "C++", "C#", "Java"
                }
        );

        table = new JTable(model);
        table.setEnabled(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(149);

        JScrollPane tableScrollPane = new JScrollPane(table);
        contentPane.add(tableScrollPane, BorderLayout.CENTER);

        chartPanel = createChartPanel(createDataset());
        contentPane.add(chartPanel, BorderLayout.SOUTH);

        startBenchmarks();
    }

    private void startBenchmarks() {
        btnNewButton.addActionListener(e -> startBenchmarkingJava());
        btnNewButton.addActionListener(e -> startBenchmarkingC());
        btnNewButton.addActionListener(e -> startBenchmarkingCSharp());
        btnNewButton.addActionListener(e -> startBenchmarkingCPlusPlus());

        frame.setSize(frame.getContentPane().getPreferredSize());
        updateChart(createDataset());
    }

    private void startBenchmarkingJava() {
        try {
            runBenchmarkJava("MemAllocStatic", 0);
            runBenchmarkJava("MemAllocDynamic", 1);
            runBenchmarkJava("Threading", 2);
            runBenchmarkJava("ThreadContextSwitch", 3);
            runBenchmarkJava("ThreadMigration", 4);

            updateChart(createDataset());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startBenchmarkingC() {
        try {
            runBenchmarkC("D:\\Coding\\SCSProject\\C\\MemAllocStatic\\cmake-build-debug\\MemAllocStatic.exe", 0, 1);
            runBenchmarkC("D:\\Coding\\SCSProject\\C\\MemAlloc\\cmake-build-debug\\MemAlloc.exe", 1, 1);
            runBenchmarkC("D:\\Coding\\SCSProject\\C\\Threading\\cmake-build-debug\\Threading.exe", 2, 1);
            runBenchmarkC("D:\\Coding\\SCSProject\\C\\ThreadContextSwitch\\main\\cmake-build-debug\\main.exe", 3, 1);
            runBenchmarkC("D:\\Coding\\SCSProject\\C\\ThreadMigration\\main\\cmake-build-debug\\main.exe", 4, 1);

            updateChart(createDataset());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startBenchmarkingCSharp() {
        try {
            runBenchmarkC("D:\\Coding\\SCSProject\\C#\\StaticMemAlloc\\StaticMemoryAllocatiom\\StaticMemoryAllocatiom\\bin\\Debug\\StaticMemoryAllocatiom.exe", 0, 3);
            runBenchmarkC("D:\\Coding\\SCSProject\\C#\\MemAlloc\\MemAlloc\\bin\\Debug\\MemAlloc.exe", 1, 3);
            runBenchmarkC("D:\\Coding\\SCSProject\\C#\\Threading\\Threading\\bin\\Debug\\Threading.exe", 2, 3);
            runBenchmarkC("D:\\Coding\\SCSProject\\C#\\ThreadContextSwitch\\ThreadContextSwitch\\bin\\Debug\\ThreadContextSwitch.exe", 3, 3);
            runBenchmarkC("D:\\Coding\\SCSProject\\C#\\ThreadMigration\\ThreadMigration\\bin\\Debug\\ThreadMigration.exe", 4, 3);

            updateChart(createDataset());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startBenchmarkingCPlusPlus() {
        try {
            runBenchmarkC("D:\\Coding\\SCSProject\\C++\\StaticMemAlloc\\cmake-build-debug\\StaticMemAlloc.exe", 0, 2);
            runBenchmarkC("D:\\Coding\\SCSProject\\C++\\MemAlloc\\cmake-build-debug\\Testing.exe", 1, 2);
            runBenchmarkC("D:\\Coding\\SCSProject\\C++\\Threading\\cmake-build-debug\\Threading.exe", 2, 2);
            runBenchmarkC("D:\\Coding\\SCSProject\\C++\\ThreadContextSwitch\\cmake-build-debug\\ThreadContextSwitch.exe", 3, 2);
            runBenchmarkC("D:\\Coding\\SCSProject\\C++\\ThreadMigration\\cmake-build-debug\\ThreadMigration.exe", 4, 2);

            // Update the chart with the new dataset
            updateChart(createDataset());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runBenchmarkJava(String benchmark, int rowIndex) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", "D:\\Coding\\SCSProject\\Java\\Testing\\out\\production\\Testing", benchmark);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder output = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        String result = extractNumericValue(output.toString());
        benchmarkResults[rowIndex][4] = result;
        table.getModel().setValueAt(output.toString(), rowIndex, 4);

        int exitCode = process.waitFor();
        System.out.println("Program " + benchmark + " - java exited with code: " + exitCode);
    }

    private void runBenchmarkC(String benchmark, int rowIndex, int columnIndex) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(benchmark);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder output = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        String result = extractNumericValue(output.toString());
        benchmarkResults[rowIndex][columnIndex] = result;
        table.getModel().setValueAt(output.toString(), rowIndex, columnIndex);

        int exitCode = process.waitFor();
        System.out.println("Program " + benchmark + " - exited with code: " + exitCode);
    }

    private String extractNumericValue(String output) {
        String regex = "(\\d+\\.\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(output);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "N/A";
    }

    private JPanel createChartPanel(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Benchmark Results",
                "Task",
                "Time (log (t / tMin))",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setItemMargin(0.1);

        // Set custom colors for each language
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesPaint(3, Color.ORANGE);

        // Set y-axis to logarithmic scale
        LogarithmicAxis yAxis = new LogarithmicAxis("Time (log (t / tMin))");
        plot.setRangeAxis(yAxis);

        // Add the chart to the chart panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 200));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String[] languages = {"C", "C++", "C#", "Java"};
        double minResult = Double.MAX_VALUE;

        // Find the minimum value
        for (int i = 0; i < benchmarkResults.length; i++) {
            for (int j = 1; j < benchmarkResults[i].length; j++) {
                String result = benchmarkResults[i][j];
                if (result != null) {
                    double value = Double.parseDouble(result);
                    if (value < minResult && value > 0) {
                        minResult = value;
                    }
                }
            }
        }

        // Populate the dataset with normalized values
        for (int i = 0; i < benchmarkResults.length; i++) {
            String task = benchmarkResults[i][0];
            for (int j = 1; j < benchmarkResults[i].length; j++) {
                String language = languages[j - 1];
                String result = benchmarkResults[i][j];

                if (result != null) {
                    double normalizedValue = Math.log10(Double.parseDouble(result) / minResult) + 1;
                    dataset.addValue(normalizedValue, language, task);
                }
            }
        }
        return dataset;
    }

    private void updateChart(CategoryDataset dataset) {
        chartPanel.removeAll();
        chartPanel.add(new ChartPanel(ChartFactory.createBarChart(
                "Benchmark Results",
                "Task",
                "Time (log (t / tMin))",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        )), BorderLayout.CENTER);

        frame.validate();
        frame.repaint();
    }
}
