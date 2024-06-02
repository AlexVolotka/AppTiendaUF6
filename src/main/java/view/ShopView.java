package view;

import model.Shop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShopView extends JFrame implements ActionListener, KeyListener {
    private JButton a1ContarCajaButton;
    private JButton a2AñadirProductoButton;
    private JButton a3AñadirStockButton;
    private JButton a9EliminarProductoButton;
    private JPanel menuOpciones;
    private JButton a5VerInventarioButton;

    public ShopView() {
        Shop shop = new Shop();
        shop.loadInventory();
        setContentPane(menuOpciones);
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        a1ContarCajaButton.addActionListener(this);
        a1ContarCajaButton.addKeyListener(this);
        a2AñadirProductoButton.addActionListener(this);
        a2AñadirProductoButton.addKeyListener(this);
        a3AñadirStockButton.addActionListener(this);
        a3AñadirStockButton.addKeyListener(this);
        a5VerInventarioButton.addActionListener(this);
        a5VerInventarioButton.addKeyListener(this);
        a9EliminarProductoButton.addActionListener(this);
        a9EliminarProductoButton.addKeyListener(this);

        addKeyListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        ProductView productView = new ProductView();
        Shop shop = new Shop();
        int opcion = 0;

        if (source == a1ContarCajaButton) {
            openCashView();
        }
        if (source == a2AñadirProductoButton) {
            opcion = 2;
            productView.openProductView(opcion, shop);
        }
        if (source == a3AñadirStockButton) {
            opcion = 3;
            productView.openProductView(opcion, shop);
        }
        if (source == a5VerInventarioButton) {
            InventoryView inventoryView = new InventoryView();
            inventoryView.setVisible(true);
        }
        if (source == a9EliminarProductoButton) {
            opcion = 4;
            productView.openProductView(opcion, shop);
        }

    }

    private void openCashView() {
        CashView cashView = new CashView();
        cashView.setVisible(true);
    }

    public void keyTyped(KeyEvent e) {

    }
    public void keyReleased(KeyEvent e) {
        // No necesitas implementar nada aquí
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        ProductView productView = new ProductView();
        Shop shop = new Shop();
        int opcion = 0;

        switch (key) {
            case KeyEvent.VK_1:
                openCashView();
                break;
            case KeyEvent.VK_2:
                opcion = 2;
                productView.openProductView(opcion, shop);
                break;
            case KeyEvent.VK_3:
                opcion = 3;
                productView.openProductView(opcion, shop);
                break;
            case KeyEvent.VK_5:
                InventoryView inventoryView = new InventoryView();
                inventoryView.showInventory();
                inventoryView.setVisible(true);
                break;
            case KeyEvent.VK_9:
                opcion = 4;
                productView.openProductView(opcion, shop);
                break;

        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ShopView();
        });
    }


}
