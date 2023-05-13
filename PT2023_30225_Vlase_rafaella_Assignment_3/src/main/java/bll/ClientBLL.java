package bll;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

public class ClientBLL
{
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL()
    {
        validators = new ArrayList<Validator<Client>>();
        clientDAO = new ClientDAO();
    }

    public Client findClientById(int id)
    {
        Client c = clientDAO.findById(id);
        if (c == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return c;
    }

    public List<Client> findAllClients()
    {
        List<Client> clientList = clientDAO.findAll();
        if (clientList == null)
        {
            throw new NoSuchElementException("No clients were found");
        }
        return clientList;
    }

    public Client insertClient(Client c)
    {
        return clientDAO.insert(c);
    }

    public void updateClient(Client c)
    {
        clientDAO.update(c);
    }

    public void deleteClient(Client c)
    {
        clientDAO.delete(c);
    }

    public void validateClient(Client c)
    {
        for (Validator<Client> validator : validators)
        {
            validator.validate(c);
        }
    }

}
