import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;

public class PlotGenerator {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;

    private static final int LEFT = 100;
    private static final int RIGHT = 60;
    private static final int TOP = 70;
    private static final int BOTTOM = 90;

    private static final String[] ALGORITHMS = {
            "MergeSort",
            "QuickSort",
            "DeterministicSelect",
            "ClosestPair"
    };

    private static final Color[] COLORS = {
            new Color(31, 119, 180),
            new Color(255, 127, 14),
            new Color(44, 160, 44),
            new Color(214, 39, 40)
    };

    static class DataPoint {
        int n;
        long time;
        int depth;

        DataPoint(int n, long time, int depth) {
            this.n = n;
            this.time = time;
            this.depth = depth;
        }
    }

    public static void main(String[] args) throws IOException {

        Map<String, List<DataPoint>> data = readCSV(
                "results/results.csv"
        );

        File outputDirectory = new File("docs/plots");
        outputDirectory.mkdirs();

        createTimePlot(
                data,
                "docs/plots/time-vs-n.png"
        );

        createDepthPlot(
                data,
                "docs/plots/recursion-depth-vs-n.png"
        );

        System.out.println("Plots generated successfully!");
        System.out.println(
                "Created: docs/plots/time-vs-n.png"
        );
        System.out.println(
                "Created: docs/plots/recursion-depth-vs-n.png"
        );
    }

    private static Map<String, List<DataPoint>> readCSV(
            String fileName
    ) throws IOException {

        Map<String, List<DataPoint>> data = new LinkedHashMap<>();

        for (String algorithm : ALGORITHMS) {
            data.put(algorithm, new ArrayList<>());
        }

        List<String> lines = Files.readAllLines(Path.of(fileName));

        for (int i = 1; i < lines.size(); i++) {

            String[] parts = lines.get(i).split(",");

            String algorithm = parts[0];
            String inputType = parts[1];

            if (!inputType.equals("random")) {
                continue;
            }

            int n = Integer.parseInt(parts[2]);
            long time = Long.parseLong(parts[3]);
            int depth = Integer.parseInt(parts[4]);

            if (data.containsKey(algorithm)) {
                data.get(algorithm).add(
                        new DataPoint(n, time, depth)
                );
            }
        }

        return data;
    }

    private static void createTimePlot(
            Map<String, List<DataPoint>> data,
            String fileName
    ) throws IOException {

        double maxValue = 0;

        for (List<DataPoint> points : data.values()) {
            for (DataPoint point : points) {
                maxValue = Math.max(maxValue, point.time);
            }
        }

        drawPlot(
                data,
                fileName,
                "Execution Time vs Input Size",
                "Execution Time (ns)",
                maxValue,
                true
        );
    }

    private static void createDepthPlot(
            Map<String, List<DataPoint>> data,
            String fileName
    ) throws IOException {

        double maxValue = 0;

        for (List<DataPoint> points : data.values()) {
            for (DataPoint point : points) {
                maxValue = Math.max(maxValue, point.depth);
            }
        }

        drawPlot(
                data,
                fileName,
                "Recursion Depth vs Input Size",
                "Maximum Recursion Depth",
                maxValue,
                false
        );
    }

    private static void drawPlot(
            Map<String, List<DataPoint>> data,
            String fileName,
            String title,
            String yLabel,
            double maxValue,
            boolean useTime
    ) throws IOException {

        BufferedImage image = new BufferedImage(
                WIDTH,
                HEIGHT,
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = image.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int plotWidth = WIDTH - LEFT - RIGHT;
        int plotHeight = HEIGHT - TOP - BOTTOM;

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 22));

        FontMetrics titleMetrics = g.getFontMetrics();

        g.drawString(
                title,
                (WIDTH - titleMetrics.stringWidth(title)) / 2,
                35
        );

        g.setStroke(new BasicStroke(2));
        g.drawLine(
                LEFT,
                TOP,
                LEFT,
                HEIGHT - BOTTOM
        );

        g.drawLine(
                LEFT,
                HEIGHT - BOTTOM,
                WIDTH - RIGHT,
                HEIGHT - BOTTOM
        );

        g.setFont(new Font("Arial", Font.PLAIN, 13));

        int steps = 5;

        for (int i = 0; i <= steps; i++) {

            int y = HEIGHT - BOTTOM -
                    (i * plotHeight / steps);

            g.setColor(new Color(220, 220, 220));
            g.drawLine(
                    LEFT,
                    y,
                    WIDTH - RIGHT,
                    y
            );

            double value = maxValue * i / steps;

            String label;

            if (useTime) {
                label = String.format("%.1fM", value / 1_000_000.0);
            } else {
                label = String.format("%.0f", value);
            }

            g.setColor(Color.BLACK);
            g.drawString(label, 45, y + 5);
        }

        int[] sizes = {100, 1000, 10000};

        for (int i = 0; i < sizes.length; i++) {

            int x = LEFT +
                    (i * plotWidth / (sizes.length - 1));

            g.setColor(Color.BLACK);

            String label = String.valueOf(sizes[i]);

            FontMetrics fm = g.getFontMetrics();

            g.drawString(
                    label,
                    x - fm.stringWidth(label) / 2,
                    HEIGHT - BOTTOM + 25
            );
        }

        // Axis labels
        g.setFont(new Font("Arial", Font.BOLD, 14));

        String xLabel = "Input Size (n)";

        FontMetrics xMetrics = g.getFontMetrics();

        g.drawString(
                xLabel,
                LEFT + (plotWidth - xMetrics.stringWidth(xLabel)) / 2,
                HEIGHT - 25
        );

        g.rotate(-Math.PI / 2);

        g.drawString(
                yLabel,
                -(TOP + plotHeight / 2 + 70),
                20
        );

        g.rotate(Math.PI / 2);

        // Lines
        int colorIndex = 0;

        for (String algorithm : ALGORITHMS) {

            List<DataPoint> points = data.get(algorithm);

            if (points == null || points.isEmpty()) {
                continue;
            }

            points.sort(Comparator.comparingInt(p -> p.n));

            g.setColor(COLORS[colorIndex]);
            g.setStroke(new BasicStroke(3));

            for (int i = 0; i < points.size(); i++) {

                DataPoint point = points.get(i);

                int x = LEFT +
                        (i * plotWidth / (points.size() - 1));

                double value = useTime
                        ? point.time
                        : point.depth;

                int y = HEIGHT - BOTTOM -
                        (int) ((value / maxValue) * plotHeight);

                g.fillOval(x - 5, y - 5, 10, 10);

                if (i > 0) {

                    DataPoint previous = points.get(i - 1);

                    int previousX = LEFT +
                            ((i - 1) * plotWidth /
                                    (points.size() - 1));

                    double previousValue = useTime
                            ? previous.time
                            : previous.depth;

                    int previousY = HEIGHT - BOTTOM -
                            (int) ((previousValue / maxValue)
                                    * plotHeight);

                    g.drawLine(
                            previousX,
                            previousY,
                            x,
                            y
                    );
                }
            }

            colorIndex++;
        }

        // Legend
        int legendX = LEFT + 20;
        int legendY = TOP + 20;

        g.setFont(new Font("Arial", Font.PLAIN, 13));

        colorIndex = 0;

        for (String algorithm : ALGORITHMS) {

            g.setColor(COLORS[colorIndex]);

            g.fillRect(
                    legendX,
                    legendY + colorIndex * 23,
                    18,
                    4
            );

            g.setColor(Color.BLACK);

            g.drawString(
                    algorithm,
                    legendX + 28,
                    legendY + 5 + colorIndex * 23
            );

            colorIndex++;
        }

        g.dispose();

        ImageIO.write(
                image,
                "png",
                new File(fileName)
        );
    }
}
