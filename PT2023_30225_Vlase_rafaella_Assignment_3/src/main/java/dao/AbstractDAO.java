package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.ConnectionFactory;

import javax.swing.table.DefaultTableModel;

public class AbstractDAO<T>
{
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery(String field)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    private String createSelectAll()
    {
        return "SELECT * FROM `" +
                type.getSimpleName() +
                "`";
    }

    public List<T> findAll()
    {
        // TODO:
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAll();
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return null;
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String createInsert()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" (");

        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++)
        {
            Field field = fields[i];
            sb.append(field.getName());
            if (i != fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(") VALUES (");

        for (int i = 0; i < fields.length; i++)
        {
            sb.append("?");
            if (i != fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private String createUpdate()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");

        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++)
        {
            Field field = fields[i];
            sb.append(field.getName()).append(" = ?");
            if (i != fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(" WHERE id = ?");
        return sb.toString();
    }

    private String createDelete()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE id = ?");
        return sb.toString();
    }

    public int insert(T t)
    {
        // TODO:
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createInsert();
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);

            int index = 1;
            for (Field field : type.getDeclaredFields())
            {
                field.setAccessible(true);
                Object value = field.get(t);
                statement.setObject(index, value);
                index++;
            }

            statement.executeUpdate();
            return (int) t;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(conn);
        }
        return (int) t;
    }

    public T update(T t)
    {
        // TODO:
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createUpdate();
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(query);

            int i = 1;
            for (Field field : type.getDeclaredFields())
            {
                field.setAccessible(true);
                statement.setObject(i++, field.get(t));
            }

            Field idField = type.getDeclaredField("id");
            idField.setAccessible(true);
            statement.setInt(i, (Integer)idField.get(t));

            statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            } finally {
                ConnectionFactory.close(resultSet);
                ConnectionFactory.close(statement);
                ConnectionFactory.close(conn);
            }
        return t;
    }

    public T delete(T t)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDelete();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, (Integer) t);
            statement.executeUpdate();
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:remove " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public DefaultTableModel makeTable()
    {
        String[] columnNames = new String[type.getDeclaredFields().length];
        int idx = 0;
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            columnNames[idx] = field.getName();
            idx++;
        }

        List<T> tableEntryList = findAll();
        Object[][] data = new Object[tableEntryList.size()][idx];

        int index = 0;
        for(T t: tableEntryList){
            ArrayList<Object> rowData = new ArrayList<>();
            for(Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(t);
                    rowData.add(value);
                } catch (Exception e) {e.printStackTrace();}

            }
            data[index] = rowData.toArray();
            index++;
        }
        return new DefaultTableModel(data, columnNames);
    }

}
