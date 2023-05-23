package model;

import bll.ClientBLL;
import bll.ProductBLL;

public class Orders
{
    private int id;
    private int quantity;
    private int id_client;
    private int id_product;

    public Orders() {
    }

    public Orders(int id, int quantity, int id_client, int id_product)
    {
        this();
        this.id = id;
        this.quantity = quantity;
        this.id_client = id_client;
        this.id_product = id_product;
    }

    public Orders(int quantity, int id_client, int id_product)
    {
        this();
        this.quantity = quantity;
        this.id_client = id_client;
        this.id_product = id_product;
    }

    public Orders(int id)
    {
        this.id = id;
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
        Client client = new ClientBLL().findClientById(id_client);
        Product product = new ProductBLL().findProductById(id_product);
        return  "Order ID: " + id +
                "\nClient ID: " + id_client +
                "\nClient Name: " + client.getName() +
                "\nProduct ID: " + id_product +
                "\nProduct Name: " + product.getProduct_name() +
                "\nPrice: " + product.getPrice() +
                "\nQuantity: " + quantity +
                "\n\nTotal Price: " + product.getPrice() * quantity;
    }
}
