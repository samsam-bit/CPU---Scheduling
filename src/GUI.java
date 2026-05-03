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

            int n = Integer.parseInt(nField.getText());

            JFrame inputFrame = new JFrame("Enter Process Data");
            inputFrame.setSize(500, 350);
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

            JButton run = new JButton("Run SJF");
            run.setBounds(180, 200, 120, 30);
            inputFrame.add(run);


            run.addActionListener(ev -> {

                process[] p = new process[n];

                for (int i = 0; i < n; i++) {

                    int at = Integer.parseInt(model.getValueAt(i, 1).toString());
                    int bt = Integer.parseInt(model.getValueAt(i, 2).toString());
                    int pr = Integer.parseInt(model.getValueAt(i, 3).toString());

                    p[i] = new process(i + 1, at, bt, pr);
                }


                String order = scheduler.SJF(p, n);


                String output = order + "\n\n=== RESULT ===\n";

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