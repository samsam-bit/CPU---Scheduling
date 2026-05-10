import java.util.*;

public class AlgorithmTest {
    public static void main(String[] args) {
        System.out.println("--- Testing Preemptive SJF ---");
        testSJF();
    }

    public static void testSJF() {
        // Input data: {id, arrivalTime, burstTime, priority}
        process[] processes = {
            new process(1, 0, 7, 1),
            new process(2, 2, 4, 2),
            new process(3, 4, 1, 3),
            new process(4, 5, 4, 4)
        };
        int n = processes.length;

        System.out.println("Input:");
        System.out.println("ID\tArrival\tBurst");
        for (process p : processes) {
            System.out.println(p.id + "\t" + p.arrivalTime + "\t" + p.burstTime);
        }

        // Deep copy for state preservation since scheduler modifies processes
        process[] pCopy = new process[n];
        for (int i = 0; i < n; i++) {
            pCopy[i] = new process(processes[i].id, processes[i].arrivalTime, processes[i].burstTime, processes[i].priority);
        }

        String result = scheduler.SJF(pCopy, n);
        System.out.println("\nOutput:\n" + result);
        
        System.out.println("\nDetailed Metrics:");
        for (process p : pCopy) {
            System.out.println("P" + p.id + " -> WT: " + p.wt + ", TAT: " + p.tat + ", RT: " + p.rt);
        }
    }
}
