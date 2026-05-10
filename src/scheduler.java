public class scheduler {

    public static String SJF(process[] p, int n) {

        int time = 0, completed = 0;
        StringBuilder order = new StringBuilder("Execution Order: ");
        StringBuilder gantt = new StringBuilder();

        int currentProcess = -1;
        int startTime = 0;

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

            if (idx != currentProcess) {
                if (currentProcess != -1) {
                    gantt.append("| P").append(p[currentProcess].id).append(" (").append(startTime).append("-").append(time).append(") ");
                }
                
                if (idx != -1) {
                    order.append("P").append(p[idx].id).append(" -> ");
                    // Record first time process gets CPU
                    if (p[idx].rt == -1) {
                        p[idx].rt = time - p[idx].arrivalTime;
                    }
                }
                
                currentProcess = idx;
                startTime = time;
            }
                
            if (idx == -1) {
                // Idle
            } else {
                p[idx].remainingTime--;
                if (p[idx].remainingTime == 0) {
                    completed++;
                    p[idx].tat = (time + 1) - p[idx].arrivalTime;
                    p[idx].wt = p[idx].tat - p[idx].burstTime;
                }
            }
            time++;
        }
        
        // Last process
        if (currentProcess != -1) {
            gantt.append("| P").append(p[currentProcess].id).append(" (").append(startTime).append("-").append(time).append(") ");
        }

        return "ORDER: " + order.toString() + "END\nGANTT: " + gantt.toString();
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