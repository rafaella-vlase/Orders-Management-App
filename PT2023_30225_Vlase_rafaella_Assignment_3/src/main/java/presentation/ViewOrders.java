package presentation;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Orders;
import model.Product;

public class ViewOrders extends JFrame
{
    private JPanel contentPanel;
    private JPanel ordersPanel;
    private JLabel ordersLabel;
    private JTable ordersTable;
    private JLabel quantityLabel;
    private JTextField quantityField;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton backButton;
    private JPanel clientsPanel;
    private JLabel clientsLabel;
    private JTable clientsTable;
    private JPanel productsPanel;
    private JLabel productsLabel;
    private JTable productsTable;
    private DefaultTableModel clientsTableModel;
    private JScrollPane clientsTableScrollPane;
    private DefaultTableModel productsTableModel;
    private JScrollPane productsTableScrollPane;
    private DefaultTableModel ordersTableModel;
    private JScrollPane ordersTableScrollPane;

    public ViewOrders()
    {
        super("Orders Table");
        setSize(650, 530);
        contentPanel = new JPanel(null);
        setContentPane(contentPanel);
        makeTables();
        makeOrders();
        makeListeners();
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void makeTables()
    {
        ordersPanel = new JPanel(new BorderLayout());
        ordersLabel = new JLabel("Orders:");
        ordersLabel.setBounds(10, 10, 50, 20);
        ordersTableModel = new OrderBLL().initOrdersTable();
        ordersTable = new JTable(ordersTableModel);
        ordersTable.setDefaultEditor(Object.class, null);
        ordersTableScrollPane = new JScrollPane(ordersTable);
        ordersPanel.add(ordersTableScrollPane);
        ordersPanel.setBounds(320, 40, 300, 200);
        contentPanel.add(ordersPanel);

        clientsPanel = new JPanel(new BorderLayout());
        clientsLabel = new JLabel("Clients:");
        clientsLabel.setBounds(10, 10, 50, 20);
        clientsTableModel = new ClientBLL().initClientsTable();
        clientsTable = new JTable(clientsTableModel);
        clientsTable.setDefaultEditor(Object.class, null);
        clientsTableScrollPane = new JScrollPane(clientsTable);
        clientsPanel.add(clientsTableScrollPane);
        clientsPanel.setBounds(10, 40, 300, 200);
        contentPanel.add(clientsPanel);

        productsPanel = new JPanel(new BorderLayout());
        productsLabel = new JLabel("Products:");
        productsLabel.setBounds(10, 10, 50, 20);
        productsTableModel = new ProductBLL().initProductsTable();
        productsTable = new JTable(productsTableModel);
        productsTable.setDefaultEditor(Object.class, null);
        productsTableScrollPane = new JScrollPane(productsTable);
        productsPanel.add(productsTableScrollPane);
        productsPanel.setBounds(10, 280, 300, 200);
        contentPanel.add(productsPanel);
    }

    private void makeOrders()
    {
        quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(320, 280, 50, 20);
        contentPanel.add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setEnabled(false);
        quantityField.setBounds(380, 282, 85, 20);
        contentPanel.add(quantityField);

        insertButton = new JButton("Add Order");
        insertButton.setEnabled(false);
        insertButton.setBounds(320, 340, 145, 50);
        contentPanel.add(insertButton);

        deleteButton = new JButton("Delete Orders");
        deleteButton.setEnabled(false);
        deleteButton.setBounds(480, 340, 145, 50);
        contentPanel.add(deleteButton);

        backButton = new JButton("Back");
        backButton.setBounds(320, 400, 145, 50);
        contentPanel.add(backButton);
    }

    private void makeListeners()
    {
        ListSelectionListener listSelectionListener = e -> {
            int[] clientRows = clientsTable.getSelectedRows();
            int[] productRows = productsTable.getSelectedRows();
            int[] orderRows = ordersTable.getSelectedRows();

            quantityField.setEnabled(clientRows.length == 1 && productRows.length == 1);
            deleteButton.setEnabled(orderRows.length >= 1);
        };
        clientsTable.getSelectionModel().addListSelectionListener(listSelectionListener);
        productsTable.getSelectionModel().addListSelectionListener(listSelectionListener);
        ordersTable.getSelectionModel().addListSelectionListener(listSelectionListener);
        quantityField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validQuantity();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validQuantity();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validQuantity();
            }
            void validQuantity(){
                try {
                    Integer.parseInt(quantityField.getText());
                    insertButton.setEnabled(true);
                }
                catch(NumberFormatException e){
                    insertButton.setEnabled(false);
                }
            }

        });

        insertButton.addActionListener(e -> {
            int clientRow = clientsTable.getSelectedRow();
            int productRow = productsTable.getSelectedRow();

            int clientID = Integer.parseInt(clientsTable.getValueAt(clientRow, 0).toString());
            int productID = Integer.parseInt(productsTable.getValueAt(productRow, 0).toString());
            int quantity = Integer.parseInt(quantityField.getText());

            ProductBLL productBLL = new ProductBLL();
            Product product = productBLL.findProductById(productID);
            product.setStock(product.getStock() - quantity);

            productBLL.validateProduct(product);

            productBLL.updateProduct(product);
            OrderBLL orderBLL = new OrderBLL();
            Orders order = new Orders(clientID, productID, quantity);
            int idValue = orderBLL.insertOrder(order);
            order.setId(idValue);
            productsTable.setValueAt(product.getStock(), productsTable.getSelectedRow(), 2);
            ordersTableModel.addRow(new Object[]{idValue, clientID, productID, quantity});
            order.makeBill();
        });

        deleteButton.addActionListener(e -> {
            int[] rows = ordersTable.getSelectedRows();
            int id;
            OrderBLL orderBLL = new OrderBLL();
            for(int i = 0; i < rows.length; i++){
                id = Integer.parseInt(ordersTableModel.getValueAt(rows[i] - i, 0).toString());
                orderBLL.deleteOrder(new Orders(id));
                ordersTableModel.removeRow(rows[i] - i);
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new Main();
        });
    }
}
