package bll;

import bll.validators.Validator;
import dao.OrderDAO;
import model.Orders;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL
{
    private List<Validator<Orders>> validators;
    private OrderDAO orderDAO;

    public OrderBLL()
    {
        validators = new ArrayList<Validator<Orders>>();
        orderDAO = new OrderDAO();
    }

    public Orders findOrderById(int id)
    {
        Orders c = orderDAO.findById(id);
        if (c == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return c;
    }

    public List<Orders> findAllOrders()
    {
        List<Orders> orderList = orderDAO.findAll();
        if(orderList == null)
        {
            throw new NoSuchElementException("No order found");
        }
        return orderList;
    }

    public int insertOrder(Orders o)
    {
        return orderDAO.insert(o).getId();
    }

    public void deleteOrder(Orders o)
    {
        orderDAO.delete(o);
    }

    public void validateOrder(Orders o)
    {
        for(Validator<Orders> validator : validators)
        {
            validator.validate(o);
        }
    }

    public DefaultTableModel initOrdersTable()
    {
        return orderDAO.makeTable();
    }
}
