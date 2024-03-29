package presentation;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame
{
    public JPanel contentPanel;
    public JButton clientButton;
    public JButton orderButton;
    public JButton productButton;

    public Main()
    {
        super("Orders Management");
        setSize(400, 400);

        contentPanel = new JPanel(new GridLayout(3, 1));
        setContentPane(contentPanel);

        clientButton = new JButton("Clients Operations");
        contentPanel.add(clientButton);
        clientButton.setBackground(new Color(229, 202, 225));

        productButton = new JButton("Products Operations");
        contentPanel.add(productButton);
        productButton.setBackground(new Color(229, 202, 225));

        orderButton = new JButton("Orders Operations");
        contentPanel.add(orderButton);
        orderButton.setBackground(new Color(229, 202, 225));

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addActionListeners();
    }

    private void addActionListeners()
    {
        clientButton.addActionListener(e -> {
            setVisible(false);
            new ViewClients().setVisible(true);
        });

        productButton.addActionListener(e -> {
            dispose();
            new ViewProducts().setVisible(true);
        });

        orderButton.addActionListener(e -> {
            dispose();
            new ViewOrders().setVisible(true);
        });
    }

}
