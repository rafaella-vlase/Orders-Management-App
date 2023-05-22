package presentation;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import bll.ProductBLL;
import model.Product;
public class ViewProducts extends JFrame
{
    private JPanel contentPanel;

    private JPanel productsPanel;
    private JTable productsTable;
    private JPanel insertPanel;
    private JLabel insertNameLabel;
    private JTextField insertNameField;
    private JLabel insertStockLabel;
    private JTextField insertStockField;
    private JLabel insertPriceLabel;
    private JTextField insertPriceField;
    private JButton insertButton;
    private JPanel updatePanel;
    private JLabel updateNameLabel;
    private JTextField updateNameField;
    private JLabel updateStockLabel;
    private JTextField updateStockField;
    private JLabel updatePriceLabel;
    private JTextField updatePriceField;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton backButton;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;


    public ViewProducts()
    {
        super("Products Table");
        setSize(650 , 500);
        contentPanel = new JPanel(null);
        contentPanel.setBackground(new Color(158, 129, 173));
        setContentPane(contentPanel);
        makeTable();
        makeInsertPanel();
        makeUpdatePanel();
        makeButtons();
        makeListeners();
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void makeTable()
    {
        productsPanel = new JPanel(new BorderLayout());
        productsPanel.setBackground(new Color(229, 202, 225));
        tableModel = new ProductBLL().initProductsTable();
        productsTable = new JTable(tableModel);
        productsTable.setOpaque(true);
        productsTable.setFillsViewportHeight(true);
        productsTable.setBackground(new Color(229, 202, 225));
        productsTable.setDefaultEditor(Object.class, null);
        tableScrollPane = new JScrollPane(productsTable);
        productsPanel.add(tableScrollPane);
        productsPanel.setBounds(10, 10, 300, 200);
        contentPanel.add(productsPanel);
    }

    private void makeInsertPanel()
    {
        insertPanel = new JPanel(null);
        insertPanel.setBackground(new Color(229, 202, 225));
        insertNameLabel = new JLabel("Name");
        insertNameLabel.setBounds(15, 45, 50, 15);
        insertNameField = new JTextField();
        insertNameField.setBounds(75, 45, 200, 20);
        insertStockLabel = new JLabel("Stock");
        insertStockLabel.setBounds(15, 70, 50, 15);
        insertStockField = new JTextField();
        insertStockField.setBounds(75, 70, 200, 20);
        insertPriceLabel = new JLabel("Price");
        insertPriceLabel.setBounds(15, 95, 50, 15);
        insertPriceField = new JTextField();
        insertPriceField.setBounds(75, 95, 200, 20);
        insertButton = new JButton("Insert Product");
        insertButton.setEnabled(false);
        insertButton.setBounds(75, 125, 200, 70);
        insertButton.setBackground(new Color(158, 129, 173));
        insertPanel.add(insertNameLabel);
        insertPanel.add(insertNameField);
        insertPanel.add(insertStockLabel);
        insertPanel.add(insertStockField);
        insertPanel.add(insertPriceLabel);
        insertPanel.add(insertPriceField);
        insertPanel.add(insertButton);
        contentPanel.add(insertPanel);
        insertPanel.setBounds(10, 220,  300, 210);
        insertPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void makeUpdatePanel()
    {
        updatePanel = new JPanel(null);
        updatePanel.setBackground(new Color(229, 202, 225));
        updateNameLabel = new JLabel("Name");
        updateNameLabel.setBounds(15, 45, 50, 15);
        updateNameField = new JTextField();
        updateNameField.setBounds(75, 45, 200, 20);
        updateStockLabel = new JLabel("Stock");
        updateStockLabel.setBounds(15, 70, 50, 15);
        updateStockField = new JTextField();
        updateStockField.setBounds(75, 70, 200, 20);
        updatePriceLabel = new JLabel("Price");
        updatePriceLabel.setBounds(15, 95, 50, 15);
        updatePriceField = new JTextField();
        updatePriceField.setBounds(75, 95, 200, 20);
        updateButton = new JButton("Update Product");
        updateButton.setBackground(new Color(158, 129, 173));
        updateButton.setBounds(75, 125, 200, 70);
        updateButton.setEnabled(false);
        updatePanel.add(updateNameLabel);
        updatePanel.add(updateNameField);
        updatePanel.add(updateStockLabel);
        updatePanel.add(updateStockField);
        updatePanel.add(updatePriceLabel);
        updatePanel.add(updatePriceField);
        updatePanel.add(updateButton);
        contentPanel.add(updatePanel);
        updatePanel.setBounds(320, 220,  300, 210);
        updatePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void makeButtons()
    {
        deleteButton = new JButton("Delete Products");
        deleteButton.setBackground(new Color(229, 202, 225));
        deleteButton.setBounds(400, 130, 200, 70);
        deleteButton.setEnabled(false);
        contentPanel.add(deleteButton);
        backButton = new JButton("Back");
        backButton.setBackground(new Color(229, 202, 225));
        backButton.setBounds(400, 10, 200, 70);
        contentPanel.add(backButton);
    }

    private void makeListeners()
    {
        productsTable.getSelectionModel().addListSelectionListener(e -> {
            int[] rows = productsTable.getSelectedRows();
            if(rows.length == 1)
            {
                updateButton.setEnabled(true);
                updateNameField.setEditable(true);
                updateStockField.setEditable(true);
                updatePriceField.setEditable(true);
                updateNameField.setText(productsTable.getValueAt(rows[0], 1).toString());
                updateStockField.setText(productsTable.getValueAt(rows[0], 2).toString());
                updatePriceField.setText(productsTable.getValueAt(rows[0], 3).toString());
            }
            else
            {
                updateButton.setEnabled(false);
                updateNameField.setEditable(false);
                updateStockField.setEditable(false);
                updatePriceField.setEditable(false);
                clearUpdateFields();
            }
            deleteButton.setEnabled(rows.length >= 1);
        });

        DocumentListener documentListener = new DocumentListener() {
            void notCleared()
            {
                insertButton.setEnabled(!(insertNameField.getText().equals("") || insertStockField.getText().equals("") || insertPriceField.getText().equals("")));
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                notCleared();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                notCleared();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                notCleared();
            }
        };
        insertNameField.getDocument().addDocumentListener(documentListener);
        insertStockField.getDocument().addDocumentListener(documentListener);
        insertPriceField.getDocument().addDocumentListener(documentListener);

        insertButton.addActionListener(e -> {
            String name = insertNameField.getText();
            int stock;
            float price;
            stock = Integer.parseInt(insertStockField.getText());
            price = Float.parseFloat(insertPriceField.getText());
            ProductBLL productBLL = new ProductBLL();
            int idValue = productBLL.insertProduct(new Product(name, stock, price));
            tableModel.addRow(new Object[]{idValue, name, stock, price});
            clearInsertFields();
        });

        updateButton.addActionListener(e -> {
            int row = productsTable.getSelectedRow();
            int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            String name = updateNameField.getText();
            int stock = Integer.parseInt(updateStockField.getText());
            float price = Float.parseFloat(updatePriceField.getText());
            ProductBLL productBLL = new ProductBLL();
            productBLL.updateProduct(new Product(id, price, name, stock));
            tableModel.setValueAt(name, row, 1);
            tableModel.setValueAt(stock, row, 2);
            tableModel.setValueAt(price, row, 3);
            clearUpdateFields();
        });

        deleteButton.addActionListener(e -> {
            int[] rows = productsTable.getSelectedRows();
            int id;
            ProductBLL productBLL = new ProductBLL();
            for(int i = 0; i < rows.length; i++){
                id = Integer.parseInt(tableModel.getValueAt(rows[i] - i, 0).toString());
                productBLL.deleteProduct(new Product(id));
                tableModel.removeRow(rows[i] - i);
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new Main();
        });
    }


    private void clearInsertFields()
    {
        insertNameField.setText("");
        insertStockField.setText("");
        insertPriceField.setText("");
    }

    private void clearUpdateFields()
    {
        updateNameField.setText("");
        updateStockField.setText("");
        updatePriceField.setText("");
    }


}
