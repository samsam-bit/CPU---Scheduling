public class process {

    int id;
    int arrivalTime;
    int burstTime;
    int priority;

    int wt;
    int tat;

    public process(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}