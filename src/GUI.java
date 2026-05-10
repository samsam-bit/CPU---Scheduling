import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("CPU Scheduler");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label = new JLabel("Number of Processes:");
        label.setBounds(20, 20, 150, 25);
        frame.add(label);

        JTextField nField = new JTextField();
        nField.setBounds(180, 20, 50, 25);
        frame.add(nField);

        JButton btn = new JButton("Create");
        btn.setBounds(120, 60, 120, 30);
        frame.add(btn);

        btn.addActionListener(e -> {

            int n;

            try {
                n = Integer.parseInt(nField.getText());
                if (n <= 0) throw new Exception();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Enter a valid number!");
                return;
            }

            frame.setVisible(false);

            JFrame inputFrame = new JFrame("Enter Process Data");
            inputFrame.setSize(500, 400);
            inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            inputFrame.setLayout(null);

            String[] cols = {"Process", "Arrival", "Burst", "Priority"};
            DefaultTableModel model = new DefaultTableModel(cols, 0);
            JTable table = new JTable(model);

            JScrollPane sp = new JScrollPane(table);
            sp.setBounds(20, 20, 440, 150);
            inputFrame.add(sp);

            for (int i = 0; i < n; i++) {
                model.addRow(new Object[]{"P" + (i + 1), "", "", ""});
            }


            JButton runSJF = new JButton("Run SJF");
            runSJF.setBounds(170, 190, 120, 30);
            inputFrame.add(runSJF);


            JButton runPR = new JButton("Run Priority");
            runPR.setBounds(170, 230, 120, 30);
            inputFrame.add(runPR);


            JButton back = new JButton("Back");
            back.setBounds(40, 300, 100, 30);
            inputFrame.add(back);


            JButton reset = new JButton("Reset");
            reset.setBounds(320, 300, 100, 30);
            inputFrame.add(reset);


            reset.addActionListener(e2 -> {
                model.setRowCount(0);
                for (int i = 0; i < n; i++) {
                    model.addRow(new Object[]{"P" + (i + 1), "", "", ""});
                }
            });

            back.addActionListener(e2 -> {
                inputFrame.dispose();
                frame.setVisible(true);
                nField.setText("");
            });


            process[] p = new process[n];

            Runnable readInput = () -> {
                for (int i = 0; i < n; i++) {
                    try {
                        int at = Integer.parseInt(model.getValueAt(i, 1).toString());
                        int bt = Integer.parseInt(model.getValueAt(i, 2).toString());
                        int pr = Integer.parseInt(model.getValueAt(i, 3).toString());

                        if (at < 0 || bt <= 0) throw new Exception();

                        p[i] = new process(i + 1, at, bt, pr);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input at row " + (i + 1));
                        throw new RuntimeException();
                    }
                }
            };

            runSJF.addActionListener(ev -> {
                try {
                    readInput.run();
                } catch (Exception ex) {
                    return;
                }

                String result = scheduler.SJF(p, n);
                String[] parts = result.split("\nGANTT: ");
                String order = parts[0].replace("ORDER: ", "");
                String gantt = parts.length > 1 ? parts[1] : "No Gantt chart available";

                String output = "Execution Sequence:\n" + order + "\n\n" +
                                "Gantt Chart:\n" + gantt + "\n\n" +
                                "=== SJF RESULT ===\n";

                for (process x : p) {
                    output += "P" + x.id +
                            " WT=" + x.wt +
                            " TAT=" + x.tat +
                            " RT=" + x.rt + "\n";
                }

                JOptionPane.showMessageDialog(null, output);
            });

            runPR.addActionListener(ev -> {
                try {
                    readInput.run();
                } catch (Exception ex) {
                    return;
                }

                String order = scheduler.Priority(p, n);

                String output = order + "\n\n=== PRIORITY RESULT ===\n";

                for (process x : p) {
                    output += "P" + x.id +
                            " WT=" + x.wt +
                            " TAT=" + x.tat + "\n";
                }

                JOptionPane.showMessageDialog(null, output);
            });

            inputFrame.setVisible(true);
        });

        frame.setVisible(true);
    }
}