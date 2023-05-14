package presentation;
import javax.swing.*;
import java.awt.*;
import model.Order;
public class ViewOrders extends JFrame
{
    private JPanel contentPanel;
    private JPanel ordersPanel;
    private JLabel ordersLabel;
    private JTable ordersTable;
    private JLabel quantityLabel;
    private JTextField quantityField;
    private JButton insertButton;

    public ViewOrders()
    {
        super("Orders Table");
        setSize(650, 530);
        contentPanel = new JPanel(null);
        setContentPane(contentPanel);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

}
