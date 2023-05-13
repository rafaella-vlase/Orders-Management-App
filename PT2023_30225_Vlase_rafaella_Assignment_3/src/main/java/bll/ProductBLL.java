package bll;

import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL
{
    private List<Validator<Product>> validators;
    private ProductDAO productDAO;

    public ProductBLL()
    {
        validators = new ArrayList<Validator<Product>>();
        productDAO = new ProductDAO();
    }

    public Product findProductById(int id)
    {
        Product c = productDAO.findById(id);
        if (c == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return c;
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

    public Product insertProduct(Product p)
    {
        return productDAO.insert(p);
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

}
