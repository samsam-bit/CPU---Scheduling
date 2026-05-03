public class Main {

    public static void main(String[] args) {

        process[] p = new process[3];

        p[0] = new process(1, 0, 5, 2);
        p[1] = new process(2, 1, 2, 1);
        p[2] = new process(3, 2, 8, 3);

        scheduler.SJF(p, 3);
        scheduler.print(p, 3);
    }
}