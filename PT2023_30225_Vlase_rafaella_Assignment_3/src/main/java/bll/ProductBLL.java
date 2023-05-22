package bll;

import bll.validators.ProductStockValidator;
import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL
{
    private List<Validator<Product>> validators;
    private ProductDAO productDAO;

    public ProductBLL()
    {
        validators = new ArrayList<>();
        validators.add(new ProductStockValidator());
        productDAO = new ProductDAO();
    }

    public Product findProductById(int id)
    {
        Product p = productDAO.findById(id);
        if (p == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return p;
    }

    public List<Product> findAllProducts()
    {
        List<Product> productList = productDAO.findAll();
        if (productList == null)
        {
            throw new NoSuchElementException("No products found");
        }
        return productList;
    }

    public int insertProduct(Product p)
    {
        return productDAO.insert(p).getId();
    }

    public void updateProduct(Product p)
    {
        productDAO.update(p);
    }

    public void deleteProduct(Product p)
    {
        productDAO.delete(p);
    }

    public void validateProduct(Product p)
    {
        for (Validator<Product> validator : validators)
        {
            validator.validate(p);
        }
    }

    public DefaultTableModel initProductsTable(){
        return productDAO.makeTable();
    }

}
