import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    // size of grid
    private int size;

    // number of trials
    private int numTrials;

    // list of all results of sims
    private double[] results;

    // sample mean
    private double sMean;

    // sample standard deviation
    private double sSD;

    // confidence int lower bound
    private double cLower;

    // confidence int upper bound
    private double cUpper;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("");
        }

        size = n;
        numTrials = trials;
        results = new double[numTrials];
        for (int i = 0; i < numTrials; i++) {
            Percolation sim = new Percolation(size);
            results[i] = sim.percSim();
        }

        // compute mean
        sMean = StdStats.mean(results);

        // compute standard deviation
        sSD = StdStats.stddev(results);

        cLower = sMean - (1.96 * sSD) / (Math.sqrt(numTrials));

        cUpper = sMean + (1.96 * sSD) / (Math.sqrt(numTrials));


    }

    // sample mean of percolation threshold
    public double mean() {
        return sMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return sSD;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return cLower;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return cUpper;
    }

    // test client (see below)
    public static void main(String[] args) {

        // ensure right number of arguments
        if (args.length < 2) {
            System.out.println(
                    "Two (positive int) arguments required: grid size and number of trials.");
            return;
        }

        // ensure arguments are ints
        int n, trials;
        try {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Arguments must be integers.");
        }

        // ensure arguments are positive
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Arguments must be greater than zero.");
        }

        // time run
        Stopwatch stopwatch = new Stopwatch();

        // run stat analysis given user inputs
        PercolationStats statTest = new PercolationStats(n, trials);

        // record time
        double time = stopwatch.elapsedTime();

        // print relevant statistics to terminal
        System.out.println("Mean               : " + statTest.mean());
        System.out.println("Standard Deviation : " + statTest.stddev());
        System.out.println("Confidence Interval: [" + statTest.confidenceLow() + ", "
                                   + statTest.confidenceHigh() + "]");
        System.out.println("Elapsed time       : " + time + " seconds.");

    }

}
