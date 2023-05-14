package presentation;
import javax.swing.*;
import java.awt.*;
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

    public ViewProducts()
    {
        super("Products Table");
        setSize(650 , 500);
        contentPanel = new JPanel(null);
        setContentPane(contentPanel);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

}
