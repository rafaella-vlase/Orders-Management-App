package bll.validators;

import model.Product;

public class ProductStockValidator implements Validator<Product>{

    @Override
    public void validate(Product product) throws IllegalArgumentException{
        if(product.getStock() < 0) {
            throw new IllegalArgumentException("Not enough " + product.getProduct_name() + " in stock");
        }
    }
}
