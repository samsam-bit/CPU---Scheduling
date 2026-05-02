public class process {
    int id, at, bt, priority;
    int start, completion, wt, tat, rt;
    boolean finished = false;

    public process(int id, int at, int bt, int priority) {
        this.id = id;
        this.at = at;
        this.bt = bt;
        this.priority = priority;
    }
}