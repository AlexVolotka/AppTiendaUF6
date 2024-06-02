package view;

import model.Amount;
import model.Shop;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CashView extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField cashUser;

    public CashView() {
        Shop shop = new Shop();
        Amount cash = shop.cash;
        String cashExtract = cash.toString();

        cashUser.setFocusable(false);

        Pattern pattern = Pattern.compile("Amount \\[value=(\\d+\\.?\\d*)€\\]");
        Matcher matcher = pattern.matcher(cashExtract);

        if (matcher.find()) {
            String amount = matcher.group(1);
            String value = "€";

            cashUser.setText(amount + value);
        } else {
            cashUser.setText("");
        }


        setContentPane(contentPane);
        setTitle("Caja");
        setSize(new Dimension(450, 200));
        setLocationRelativeTo(null);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
        CashView dialog = new CashView();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
