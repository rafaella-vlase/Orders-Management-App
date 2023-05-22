package start;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bll.ProductBLL;
import model.Product;

public class Test {
    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

    public static void main(String[] args) throws SQLException {


        ProductBLL productBLL = new ProductBLL();

        Product product = null;

        try {
            product = productBLL.findProductById(1);

        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
        }

        // obtain field-value pairs for object through reflection
        //ReflectionExample.retrieveProperties(product);
    }
}