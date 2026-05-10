import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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


            JButton runSJF = new JButton("Run SJF (Pre)");
            runSJF.setBounds(20, 190, 120, 30);
            inputFrame.add(runSJF);

            JButton runSJFN = new JButton("Run SJF (Non-Pre)");
            runSJFN.setBounds(150, 190, 130, 30);
            inputFrame.add(runSJFN);

            JButton runPR = new JButton("Run Priority");
            runPR.setBounds(290, 190, 100, 30);
            inputFrame.add(runPR);

            JButton runBoth = new JButton("Run All (Compare)");
            runBoth.setBounds(140, 230, 160, 30);
            inputFrame.add(runBoth);


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
            String[] resultCols = {"Process", "WT", "TAT", "RT"};

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

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JTextArea sequenceArea = new JTextArea("Execution Sequence:\n" + order);
                sequenceArea.setEditable(false);
                sequenceArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                sequenceArea.setOpaque(false);
                panel.add(sequenceArea);

                JTextArea ganttArea = new JTextArea("Gantt Chart:\n" + gantt);
                ganttArea.setEditable(false);
                ganttArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                ganttArea.setOpaque(false);
                panel.add(ganttArea);

                DefaultTableModel tableModel = new DefaultTableModel(resultCols, 0);
                double wtSum = 0, tatSum = 0, rtSum = 0;
                for (process x : p) {
                    tableModel.addRow(new Object[]{x.id, x.wt, x.tat, x.rt});
                    wtSum += x.wt;
                    tatSum += x.tat;
                    rtSum += x.rt;
                }
                tableModel.addRow(new Object[]{"Avg", String.format("%.2f", wtSum / n),
                    String.format("%.2f", tatSum / n), String.format("%.2f", rtSum / n)});
                JTable resultTable = new JTable(tableModel);
                resultTable.setFont(new Font("Monospaced", Font.PLAIN, 12));
                JScrollPane tableScroll = new JScrollPane(resultTable);
                panel.add(new JLabel("=== SJF RESULTS ==="));
                panel.add(tableScroll);

                JOptionPane.showMessageDialog(null, panel);
            });

            runSJFN.addActionListener(ev -> {
                try {
                    readInput.run();
                } catch (Exception ex) {
                    return;
                }

                String result = scheduler.SJF_NonPreemptive(p, n);
                String[] parts = result.split("\nGANTT: ");
                String order = parts[0].replace("ORDER: ", "");
                String gantt = parts.length > 1 ? parts[1] : "No Gantt chart available";

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JTextArea sequenceArea = new JTextArea("Execution Sequence:\n" + order);
                sequenceArea.setEditable(false);
                sequenceArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                sequenceArea.setOpaque(false);
                panel.add(sequenceArea);

                JTextArea ganttArea = new JTextArea("Gantt Chart:\n" + gantt);
                ganttArea.setEditable(false);
                ganttArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                ganttArea.setOpaque(false);
                panel.add(ganttArea);

                DefaultTableModel tableModel = new DefaultTableModel(resultCols, 0);
                double wtSum = 0, tatSum = 0, rtSum = 0;
                for (process x : p) {
                    tableModel.addRow(new Object[]{x.id, x.wt, x.tat, x.rt});
                    wtSum += x.wt;
                    tatSum += x.tat;
                    rtSum += x.rt;
                }
                tableModel.addRow(new Object[]{"Avg", String.format("%.2f", wtSum / n),
                    String.format("%.2f", tatSum / n), String.format("%.2f", rtSum / n)});
                JTable resultTable = new JTable(tableModel);
                resultTable.setFont(new Font("Monospaced", Font.PLAIN, 12));
                JScrollPane tableScroll = new JScrollPane(resultTable);
                panel.add(new JLabel("=== SJF (Non-Preemptive) RESULTS ==="));
                panel.add(tableScroll);

                JOptionPane.showMessageDialog(null, panel);
            });

            runPR.addActionListener(ev -> {
                try {
                    readInput.run();
                } catch (Exception ex) {
                    return;
                }

                String result = scheduler.Priority(p, n);
                String[] parts = result.split("\nGANTT: ");
                String order = parts[0].replace("ORDER: ", "");
                String gantt = parts.length > 1 ? parts[1] : "No Gantt chart available";

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JTextArea sequenceArea = new JTextArea("Execution Sequence:\n" + order);
                sequenceArea.setEditable(false);
                sequenceArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                sequenceArea.setOpaque(false);
                panel.add(sequenceArea);

                JTextArea ganttArea = new JTextArea("Gantt Chart:\n" + gantt);
                ganttArea.setEditable(false);
                ganttArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                ganttArea.setOpaque(false);
                panel.add(ganttArea);

                DefaultTableModel tableModel = new DefaultTableModel(resultCols, 0);
                double wtSum = 0, tatSum = 0, rtSum = 0;
                for (process x : p) {
                    tableModel.addRow(new Object[]{x.id, x.wt, x.tat, x.rt});
                    wtSum += x.wt;
                    tatSum += x.tat;
                    rtSum += x.rt;
                }
                tableModel.addRow(new Object[]{"Avg", String.format("%.2f", wtSum / n),
                    String.format("%.2f", tatSum / n), String.format("%.2f", rtSum / n)});
                JTable resultTable = new JTable(tableModel);
                resultTable.setFont(new Font("Monospaced", Font.PLAIN, 12));
                JScrollPane tableScroll = new JScrollPane(resultTable);
                panel.add(new JLabel("=== PRIORITY RESULTS ==="));
                panel.add(tableScroll);

                JOptionPane.showMessageDialog(null, panel);
            });

            runBoth.addActionListener(ev -> {
                try {
                    readInput.run();
                } catch (Exception ex) {
                    return;
                }

                process[] pSJF = new process[n];
                process[] pSJFN = new process[n];
                process[] pPR = new process[n];
                for (int i = 0; i < n; i++) {
                    pSJF[i] = new process(p[i].id, p[i].arrivalTime, p[i].burstTime, p[i].priority);
                    pSJFN[i] = new process(p[i].id, p[i].arrivalTime, p[i].burstTime, p[i].priority);
                    pPR[i] = new process(p[i].id, p[i].arrivalTime, p[i].burstTime, p[i].priority);
                }

                String sjfResult = scheduler.SJF(pSJF, n);
                String[] sjfParts = sjfResult.split("\nGANTT: ");
                String sjfOrder = sjfParts[0].replace("ORDER: ", "");
                String sjfGantt = sjfParts.length > 1 ? sjfParts[1] : "";

                String sjfnResult = scheduler.SJF_NonPreemptive(pSJFN, n);
                String[] sjfnParts = sjfnResult.split("\nGANTT: ");
                String sjfnOrder = sjfnParts[0].replace("ORDER: ", "");
                String sjfnGantt = sjfnParts.length > 1 ? sjfnParts[1] : "";

                String prResult = scheduler.Priority(pPR, n);
                String[] prParts = prResult.split("\nGANTT: ");
                String prOrder = prParts[0].replace("ORDER: ", "");
                String prGantt = prParts.length > 1 ? prParts[1] : "";

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JLabel title = new JLabel("=== ALGORITHM COMPARISON ===");
                title.setFont(new Font("Arial", Font.BOLD, 14));
                panel.add(title);
                panel.add(new JSeparator());

                panel.add(new JLabel("SJF (Preemptive)"));
                panel.add(new JTextArea("Sequence: " + sjfOrder));
                JTextArea sjfGanttArea = new JTextArea("Gantt: " + sjfGantt);
                sjfGanttArea.setEditable(false);
                sjfGanttArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
                sjfGanttArea.setOpaque(false);
                panel.add(sjfGanttArea);

                DefaultTableModel sjfTableModel = new DefaultTableModel(resultCols, 0);
                double sjfWT = 0, sjfTAT = 0, sjfRT = 0;
                for (int i = 0; i < n; i++) {
                    sjfTableModel.addRow(new Object[]{pSJF[i].id, pSJF[i].wt, pSJF[i].tat, pSJF[i].rt});
                    sjfWT += pSJF[i].wt;
                    sjfTAT += pSJF[i].tat;
                    sjfRT += pSJF[i].rt;
                }
                sjfTableModel.addRow(new Object[]{"Avg", String.format("%.2f", sjfWT / n),
                    String.format("%.2f", sjfTAT / n), String.format("%.2f", sjfRT / n)});
                JTable sjfTable = new JTable(sjfTableModel);
                sjfTable.setFont(new Font("Monospaced", Font.PLAIN, 11));
                panel.add(new JScrollPane(sjfTable));

                panel.add(new JSeparator());
                panel.add(new JLabel("SJF (Non-Preemptive)"));
                panel.add(new JTextArea("Sequence: " + sjfnOrder));
                JTextArea sjfnGanttArea = new JTextArea("Gantt: " + sjfnGantt);
                sjfnGanttArea.setEditable(false);
                sjfnGanttArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
                sjfnGanttArea.setOpaque(false);
                panel.add(sjfnGanttArea);

                DefaultTableModel sjfnTableModel = new DefaultTableModel(resultCols, 0);
                double sjfnWT = 0, sjfnTAT = 0, sjfnRT = 0;
                for (int i = 0; i < n; i++) {
                    sjfnTableModel.addRow(new Object[]{pSJFN[i].id, pSJFN[i].wt, pSJFN[i].tat, pSJFN[i].rt});
                    sjfnWT += pSJFN[i].wt;
                    sjfnTAT += pSJFN[i].tat;
                    sjfnRT += pSJFN[i].rt;
                }
                sjfnTableModel.addRow(new Object[]{"Avg", String.format("%.2f", sjfnWT / n),
                    String.format("%.2f", sjfnTAT / n), String.format("%.2f", sjfnRT / n)});
                JTable sjfnTable = new JTable(sjfnTableModel);
                sjfnTable.setFont(new Font("Monospaced", Font.PLAIN, 11));
                panel.add(new JScrollPane(sjfnTable));

                panel.add(new JSeparator());
                panel.add(new JLabel("PRIORITY (Non-Preemptive)"));
                panel.add(new JTextArea("Sequence: " + prOrder));
                JTextArea prGanttArea = new JTextArea("Gantt: " + prGantt);
                prGanttArea.setEditable(false);
                prGanttArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
                prGanttArea.setOpaque(false);
                panel.add(prGanttArea);

                DefaultTableModel prTableModel = new DefaultTableModel(resultCols, 0);
                double prWT = 0, prTAT = 0, prRT = 0;
                for (int i = 0; i < n; i++) {
                    prTableModel.addRow(new Object[]{pPR[i].id, pPR[i].wt, pPR[i].tat, pPR[i].rt});
                    prWT += pPR[i].wt;
                    prTAT += pPR[i].tat;
                    prRT += pPR[i].rt;
                }
                prTableModel.addRow(new Object[]{"Avg", String.format("%.2f", prWT / n),
                    String.format("%.2f", prTAT / n), String.format("%.2f", prRT / n)});
                JTable prTable = new JTable(prTableModel);
                prTable.setFont(new Font("Monospaced", Font.PLAIN, 11));
                panel.add(new JScrollPane(prTable));

                JScrollPane scrollPane = new JScrollPane(panel);
                scrollPane.setPreferredSize(new Dimension(500, 500));
                JOptionPane.showMessageDialog(null, scrollPane);
            });

            inputFrame.setVisible(true);
        });

        frame.setVisible(true);
    }
}