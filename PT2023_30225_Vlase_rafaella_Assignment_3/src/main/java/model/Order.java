package model;

public class Order
{
    private int id;
    private int quantity;
    private int id_client;
    private int id_product;

    public Order() {
    }

    public Order(int id, int quantity, int id_client, int id_product)
    {
        this();
        this.id = id;
        this.quantity = quantity;
        this.id_client = id_client;
        this.id_product = id_product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    @Override
    public String toString()
    {
        return "Order [id=" + id + ", quantity=" + quantity + ", ID client=" + id_client + ", ID product=" + id_product + "]";
    }
}
