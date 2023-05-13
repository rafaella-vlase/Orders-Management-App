package bll;

import bll.validators.Validator;
import dao.OrderDAO;
import model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL
{
    private List<Validator<Order>> validators;
    private OrderDAO orderDAO;

    public OrderBLL()
    {
        validators = new ArrayList<Validator<Order>>();
        orderDAO = new OrderDAO();
    }

    public Order findOrderById(int id)
    {
        Order c = orderDAO.findById(id);
        if (c == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return c;
    }

    public List<Order> findAllOrders()
    {
        List<Order> orderList = orderDAO.findAll();
        if(orderList == null)
        {
            throw new NoSuchElementException("No order found");
        }
        return orderList;
    }

    public Order insertOrder(Order o)
    {
        return orderDAO.insert(o);
    }

    public void deleteOrder(Order o)
    {
        orderDAO.delete(o);
    }

    public void validateOrder(Order o)
    {
        for(Validator<Order> validator : validators)
        {
            validator.validate(o);
        }
    }

}
