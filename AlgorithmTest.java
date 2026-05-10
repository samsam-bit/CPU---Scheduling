public class AlgorithmTest {
    public static process[] copyProcesses(process[] original) {
        process[] copy = new process[original.length];
        for (int i = 0; i < original.length; i++) {
            copy[i] = new process(original[i].id, original[i].arrivalTime, original[i].burstTime, original[i].priority);
        }
        return copy;
    }

    public static void printMetrics(process[] p, String label) {
        System.out.println("=== " + label + " ===");
        System.out.println("Process\tWT\tTAT\tRT");
        double wtSum = 0, tatSum = 0, rtSum = 0;
        for (process x : p) {
            System.out.println("P" + x.id + "\t" + x.wt + "\t" + x.tat + "\t" + x.rt);
            wtSum += x.wt;
            tatSum += x.tat;
            rtSum += x.rt;
        }
        int n = p.length;
        System.out.println("Average:\t" + String.format("%.2f", wtSum / n) +
                          "\t" + String.format("%.2f", tatSum / n) +
                          "\t" + String.format("%.2f", rtSum / n));
        System.out.println();
    }

    public static void main(String[] args) {
        process[] processes = {
            new process(1, 0, 7, 2),
            new process(2, 2, 4, 1),
            new process(3, 4, 1, 3),
            new process(4, 5, 4, 4)
        };
        int n = processes.length;

        System.out.println("Input:");
        System.out.println("ID\tArrival\tBurst\tPriority");
        for (process p : processes) {
            System.out.println(p.id + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" + p.priority);
        }
        System.out.println();

        process[] pSJF = copyProcesses(processes);
        process[] pSJFN = copyProcesses(processes);
        process[] pPR = copyProcesses(processes);

        scheduler.SJF(pSJF, n);
        scheduler.SJF_NonPreemptive(pSJFN, n);
        scheduler.Priority(pPR, n);

        printMetrics(pSJF, "SJF (Preemptive)");
        printMetrics(pSJFN, "SJF (Non-Preemptive)");
        printMetrics(pPR, "Priority (Non-Preemptive)");
    }
}
