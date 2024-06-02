package view;
import model.Employee;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import exception.LimitLoginException;

public class LoginView extends JFrame implements ActionListener {
    private JTextField numEmpleado;
    private JTextField passwd;
    private JButton accederButton;
    private JPanel LoginPage;

    public int errorLogCount = 1;

    public LoginView() {
        setContentPane(LoginPage);
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        accederButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == accederButton) {
            loginCheck();
        }

    }

    public void loginCheck() {
        try {
            boolean logged;

            int iduser = Integer.parseInt(numEmpleado.getText());
            String password = passwd.getText();

            Employee employee = new Employee("", iduser);

            logged = employee.login(iduser, password);

            if (logged) {
                openMenu();
            } else {
                errorLogCount++;
                JOptionPane.showMessageDialog(null, "Error: Datos introducidos incorrectos, le quedan " + (4 - errorLogCount) + " intentos", "Error", JOptionPane.ERROR_MESSAGE);
                numEmpleado.setText("");
                passwd.setText("");
                if (errorLogCount >= 4) {
                    try {
                        throw new LimitLoginException("Demasiados intentos de inicio de sesion fallidos");
                    } catch (LimitLoginException ex) {
                        JOptionPane.showMessageDialog(null, "Error: Se han superado el limite de intentos", "Error", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Login Failed" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void openMenu() {
        setVisible(false);
        ShopView shopView = new ShopView();
        shopView.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }

}
