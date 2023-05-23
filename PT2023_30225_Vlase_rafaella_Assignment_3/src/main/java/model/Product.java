package model;

public class Product
{
    private int id;
    private float price;
    private String product_name;
    private int stock;

    public Product(){
    }

    public Product(int id, float price, String product_name, int stock)
    {
        super();
        this.id = id;
        this.price = price;
        this.product_name = product_name;
        this.stock = stock;
    }

    public Product(float price, String name, int stock)
    {
        super();
        this.price = price;
        this.product_name = name;
        this.stock = stock;
    }

    public Product(int id)
    {
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString()
    {
        return "Product [id=" + id + ", price=" + price + ", name=" + product_name + ", in stock=" + stock + "]";
    }
}
