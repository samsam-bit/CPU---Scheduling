public class scheduler {

    public static String SJF(process[] p, int n) {

        boolean[] done = new boolean[n];
        int time = 0;
        int completed = 0;

        String order = "Execution Order: ";

        while (completed < n) {

            int idx = -1;
            int minBurst = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {

                if (!done[i] && p[i].arrivalTime <= time) {

                    if (p[i].burstTime < minBurst) {
                        minBurst = p[i].burstTime;
                        idx = i;
                    }
                }
            }

            if (idx == -1) {
                time++;
            } else {

                order += "P" + p[idx].id + " -> ";

                p[idx].wt = time - p[idx].arrivalTime;
                time += p[idx].burstTime;
                p[idx].tat = p[idx].wt + p[idx].burstTime;

                done[idx] = true;
                completed++;
            }
        }

        return order + "END";
    }
    public static void print(process[] p, int n) {

        System.out.println("\n=== RESULT ===");

        for (int i = 0; i < n; i++) {
            System.out.println(
                    "P" + p[i].id +
                            " WT=" + p[i].wt +
                            " TAT=" + p[i].tat
            );
        }
    }
}