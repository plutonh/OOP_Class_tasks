package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE

        AList Ns = new AList();
        AList times = new AList();
        AList opCounts = new AList();

        Ns.addLast(1000);
        Ns.addLast(2000);
        Ns.addLast(4000);
        Ns.addLast(8000);
        Ns.addLast(16000);
        Ns.addLast(32000);
        Ns.addLast(64000);
        Ns.addLast(128000);

        times.addLast(0.00);
        times.addLast(0.01);
        times.addLast(0.01);
        times.addLast(0.04);
        times.addLast(0.10);
        times.addLast(0.50);
        times.addLast(1.15);
        times.addLast(3.74);

        opCounts.addLast(10000);
        opCounts.addLast(10000);
        opCounts.addLast(10000);
        opCounts.addLast(10000);
        opCounts.addLast(10000);
        opCounts.addLast(10000);
        opCounts.addLast(10000);
        opCounts.addLast(10000);

        printTimingTable(Ns, times, opCounts);
    }

}
