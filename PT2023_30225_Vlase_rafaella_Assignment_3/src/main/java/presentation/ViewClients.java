package presentation;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import bll.ClientBLL;
import model.Client;
public class ViewClients extends JFrame
{
    private JPanel contentPanel;
    private JPanel clientsPanel;
    private JPanel insertPanel;
    private JLabel insertNameLabel;
    private JTextField insertNameField;
    private JLabel insertAddressLabel;
    private JTextField insertAddressField;
    private JLabel insertEmailLabel;
    private JTextField insertEmailField;
    private JButton insertButton;
    private JPanel updatePanel;
    private JLabel updateNameLabel;
    private JTextField updateNameField;
    private JLabel updateAddressLabel;
    private JTextField updateAddressField;
    private JLabel updateEmailLabel;
    private JTextField updateEmailField;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton backButton;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;
    private JTable clientsTable;

    public ViewClients()
    {
        super("Clients Table");
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
        clientsPanel = new JPanel(new BorderLayout());
        clientsPanel.setBackground(new Color(229, 202, 225));
        tableModel = new ClientBLL().initClientsTable();
        clientsTable = new JTable(tableModel);
        clientsTable.setBackground(new Color(229, 202, 225));
        clientsTable.setDefaultEditor(Object.class, null);
        tableScrollPane = new JScrollPane(clientsTable);
        clientsPanel.add(tableScrollPane);
        clientsPanel.setBounds(10, 10, 300, 200);
        contentPanel.add(clientsPanel);
    }

    private void makeInsertPanel()
    {
        insertPanel = new JPanel(null);
        insertPanel.setBackground(new Color(229, 202, 225));
        insertNameLabel = new JLabel("Name");
        insertNameLabel.setBounds(15, 45, 50, 15);
        insertNameField = new JTextField();
        insertNameField.setBounds(75, 45, 200, 20);
        insertAddressLabel = new JLabel("Address");
        insertAddressLabel.setBounds(15, 70, 50, 15);
        insertAddressField = new JTextField();
        insertAddressField.setBounds(75, 70, 200, 20);
        insertEmailLabel = new JLabel("Email");
        insertEmailLabel.setBounds(15, 95, 50, 15);
        insertEmailField = new JTextField();
        insertEmailField.setBounds(75, 95, 200, 20);
        insertButton = new JButton("Insert Client");
        insertButton.setEnabled(false);
        insertButton.setBounds(75, 125, 200, 70);
        insertButton.setBackground(new Color(158, 129, 173));
        insertPanel.add(insertNameLabel);
        insertPanel.add(insertNameField);
        insertPanel.add(insertAddressLabel);
        insertPanel.add(insertAddressField);
        insertPanel.add(insertEmailLabel);
        insertPanel.add(insertEmailField);
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
        updateAddressLabel = new JLabel("Address");
        updateAddressLabel.setBounds(15, 70, 50, 15);
        updateAddressField = new JTextField();
        updateAddressField.setBounds(75, 70, 200, 20);
        updateEmailLabel = new JLabel("Email");
        updateEmailLabel.setBounds(15, 95, 50, 15);
        updateEmailField = new JTextField();
        updateEmailField.setBounds(75, 95, 200, 20);
        updateButton = new JButton("Update Client");
        updateButton.setBackground(new Color(158, 129, 173));
        updateButton.setBounds(75, 125, 200, 70);
        updateButton.setEnabled(false);
        updatePanel.add(updateNameLabel);
        updatePanel.add(updateNameField);
        updatePanel.add(updateAddressLabel);
        updatePanel.add(updateAddressField);
        updatePanel.add(updateEmailLabel);
        updatePanel.add(updateEmailField);
        updatePanel.add(updateButton);
        contentPanel.add(updatePanel);
        updatePanel.setBounds(320, 220,  300, 210);
        updatePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void makeButtons()
    {
        deleteButton = new JButton("Delete Clients");
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
        clientsTable.getSelectionModel().addListSelectionListener(e -> {
            int[] rows = clientsTable.getSelectedRows();
            if(rows.length == 1)
            {
                updateButton.setEnabled(true);
                updateNameField.setEditable(true);
                updateAddressField.setEditable(true);
                updateEmailField.setEditable(true);
                updateNameField.setText(clientsTable.getValueAt(rows[0], 1).toString());
                updateAddressField.setText(clientsTable.getValueAt(rows[0], 2).toString());
                updateEmailField.setText(clientsTable.getValueAt(rows[0], 3).toString());
            }
            else
            {
                updateButton.setEnabled(false);
                updateNameField.setEditable(false);
                updateAddressField.setEditable(false);
                updateEmailField.setEditable(false);
                clearUpdateFields();
            }
            deleteButton.setEnabled(rows.length >= 1);
        });

        DocumentListener documentListener = new DocumentListener() {

            void notCleared()
            {
                insertButton.setEnabled(!(insertNameField.getText().equals("") || insertAddressField.getText().equals("") || insertEmailField.getText().equals("")));
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
        insertAddressField.getDocument().addDocumentListener(documentListener);
        insertEmailField.getDocument().addDocumentListener(documentListener);

        insertButton.addActionListener(e -> {
            String name = insertNameField.getText();
            String address = insertAddressField.getText();
            String email = insertEmailField.getText();
            ClientBLL clientBLL = new ClientBLL();
            int idValue = clientBLL.insertClient(new Client(name, address, email));
            tableModel.addRow(new Object[]{idValue, name, address, email});
            clearInsertFields();
        });

        updateButton.addActionListener(e -> {
            int row = clientsTable.getSelectedRow();
            int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            String name = updateNameField.getText();
            String address = updateAddressField.getText();
            String email = updateEmailField.getText();
            ClientBLL clientBLL = new ClientBLL();
            clientBLL.updateClient(new Client(id, name, address, email));
            tableModel.setValueAt(name, row, 1);
            tableModel.setValueAt(address, row, 2);
            tableModel.setValueAt(email, row, 3);
            clearUpdateFields();
        });

        deleteButton.addActionListener(e -> {
            int[] rows = clientsTable.getSelectedRows();
            int id;
            ClientBLL clientBLL = new ClientBLL();
            for(int i = 0; i < rows.length; i++)
            {
                id = Integer.parseInt(tableModel.getValueAt(rows[i] - i, 0).toString());
                clientBLL.deleteClient(new Client(id));
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
        insertAddressField.setText("");
        insertEmailField.setText("");
    }

    private void clearUpdateFields()
    {
        updateNameField.setText("");
        updateAddressField.setText("");
        updateEmailField.setText("");
    }
}
