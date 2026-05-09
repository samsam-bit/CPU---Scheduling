public class scheduler {

    public static String SJF(process[] p, int n) {

        int time = 0, completed = 0;
        String order = "Execution Order: ";

        while (completed < n) {

            int idx = -1;
            int min = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {

                if (p[i].arrivalTime <= time && p[i].remainingTime > 0) {

                    if (p[i].remainingTime < min) {
                        min = p[i].remainingTime;
                        idx = i;
                    }
                }
            }

            if (idx == -1) {
                time++;
            } else {

                order += "P" + p[idx].id + " -> ";

                p[idx].remainingTime--;
                time++;

                if (p[idx].remainingTime == 0) {
                    completed++;

                    int finish = time;
                    p[idx].tat = finish - p[idx].arrivalTime;
                    p[idx].wt = p[idx].tat - p[idx].burstTime;
                }
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
    public static String Priority(process[] p, int n) {

        boolean[] done = new boolean[n];
        int time = 0, completed = 0;

        String order = "Execution Order: ";

        while (completed < n) {

            int idx = -1;
            int bestPriority = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {

                if (!done[i] && p[i].arrivalTime <= time) {

                    if (p[i].priority < bestPriority) {
                        bestPriority = p[i].priority;
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
}