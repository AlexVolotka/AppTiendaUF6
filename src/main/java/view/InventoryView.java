package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.*;

public class InventoryView extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table1;

    public InventoryView() {
        contentPane = new JPanel(); // Initialize contentPane
        contentPane.setLayout(new BorderLayout()); // Set the layout

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK = new JButton("OK"); // Initialize buttonOK
        buttonCancel = new JButton("Cancel"); // Initialize buttonCancel

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        JPanel buttonPane = new JPanel(); // Create a panel for the buttons
        buttonPane.add(buttonOK);
        buttonPane.add(buttonCancel);

        contentPane.add(buttonPane, BorderLayout.SOUTH); // Add the button panel to the content pane

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // Initialize table1 component
        table1 = new JTable();
        JScrollPane scrollPane = new JScrollPane(table1);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Show inventory data
        showInventory();

        pack();
        setLocationRelativeTo(null);
    }

    public void showInventory() {
        List<String[]> data = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("inputInventory.txt"))) {
            while ((line = br.readLine())!= null) {
                data.add(line.split(";"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] columnNames = {"Código", "Nombre", "Precio público", "Precio mayorista", "Stock", "Disponible"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        int rowCount = 1;
        for (String[] row : data) {
            String[] newRow = new String[6];
            newRow[0] = String.valueOf(rowCount);
            int index = row[0].indexOf(":");
            if (index != -1) {
                newRow[1] = row[0].substring(index + 1);
            } else {
                newRow[1] = row[0];
            }

            int stockIndex = row.length - 1;
            int wholesalerPriceIndex = stockIndex - 1;
            String stockValue = row[stockIndex].split(":")[1];
            newRow[4] = stockValue;

            double wholesalerPrice = Double.parseDouble(row[wholesalerPriceIndex].split(":")[1]);
            double publicPrice = wholesalerPrice * 2;
            newRow[2] = String.valueOf(publicPrice);
            newRow[3] = String.valueOf(wholesalerPrice);

            // Agregamos la columna de disponibilidad
            if (Integer.parseInt(stockValue) > 0) {
                newRow[5] = "Disponible";
            } else {
                newRow[5] = "No Disponible";
            }

            model.addRow(newRow);
            rowCount++;
        }
        table1.setModel(model);

        // Set column widths
        table1.getColumnModel().getColumn(0).setPreferredWidth(50);
        table1.getColumnModel().getColumn(1).setPreferredWidth(150);
        table1.getColumnModel().getColumn(2).setPreferredWidth(100);
        table1.getColumnModel().getColumn(3).setPreferredWidth(100);
        table1.getColumnModel().getColumn(4).setPreferredWidth(50);
        table1.getColumnModel().getColumn(5).setPreferredWidth(100);

        // Align columns
        table1.getColumnModel().getColumn(2).setCellRenderer(new RightAlignRenderer());
        table1.getColumnModel().getColumn(3).setCellRenderer(new RightAlignRenderer());
        table1.getColumnModel().getColumn(4).setCellRenderer(new RightAlignRenderer());
    }

    public void updateInventory() {
        showInventory();
    }


    private static class RightAlignRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (c instanceof JLabel) {
                ((JLabel) c).setHorizontalAlignment(JLabel.RIGHT);
            }
            return c;
        }
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        InventoryView dialog = new InventoryView();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}