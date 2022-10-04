package com.lc.respository;

import com.lc.model.ToDoItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DELL
 * @date 2022/7/4 22:47
 */
public class H2ToDoRepository implements ToDoRepository {

    private Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        //  F:/Projects/Gradle/GradleInAction/Chapter07/listing_07_17-18-integration-test-separated/todo
        return DriverManager.getConnection("jdbc:h2:F:/Projects/Gradle/GradleInAction" +
                "/Chapter13/todo/todo", "sa", "13879640liu");
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<ToDoItem> findAll() {
        List<ToDoItem> toDoItems = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = createConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id, name, completed from todo_item");

            while (rs.next()) {
                ToDoItem toDoItem = new ToDoItem();
                toDoItem.setId(rs.getLong("id"));
                toDoItem.setName(rs.getString("name"));
                toDoItem.setCompleted(rs.getBoolean("completed"));
                toDoItems.add(toDoItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }
        return toDoItems;
    }

    @Override
    public List<ToDoItem> findAllActive() {
        List<ToDoItem> activeToDos = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = createConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id, name, completed FROM todo_item WHERE completed = 0");

            while (rs.next()) {
                ToDoItem toDoItem = new ToDoItem();
                toDoItem.setId(rs.getLong("id"));
                toDoItem.setName(rs.getString("name"));
                toDoItem.setCompleted(rs.getBoolean("completed"));
                activeToDos.add(toDoItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }
        return activeToDos;
    }

    @Override
    public List<ToDoItem> findAllCompleted() {
        List<ToDoItem> completedToDos = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = createConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id, name, completed FROM todo_item " +
                    "WHERE completed = 1");

            while (rs.next()) {
                ToDoItem toDoItem = new ToDoItem();
                toDoItem.setId(rs.getLong("id"));
                toDoItem.setName(rs.getString("name"));
                toDoItem.setCompleted(rs.getBoolean("completed"));
                completedToDos.add(toDoItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }
        return completedToDos;
    }

    @Override
    public ToDoItem findById(Long id) {
        ToDoItem toDoItem = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = createConnection();
            stmt = conn.prepareStatement("SELECT id, name, " +
                    "completed from todo_item WHERE id = ?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            if (rs.first()) {
                toDoItem = new ToDoItem();
                toDoItem.setId(rs.getLong("id"));
                toDoItem.setName(rs.getString("name"));
                toDoItem.setCompleted(rs.getBoolean("completed"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }
        return toDoItem;
    }

    @Override
    public Long insert(ToDoItem toDoItem) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Long newId = null;
        try {
            conn = createConnection();
            stmt = conn.prepareStatement("INSERT INTO todo_item(" +
                    "name, completed) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, toDoItem.getName());
            stmt.setBoolean(2, toDoItem.isCompleted());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                newId = rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }
        return newId;
    }

    @Override
    public void update(ToDoItem toDoItem) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = createConnection();
            stmt = conn.prepareStatement("UPDATE todo_item SET  " +
                    "name = ?, completed = ? where id = ?");
            stmt.setString(1, toDoItem.getName());
            stmt.setBoolean(2, toDoItem.isCompleted());
            stmt.setLong(3, toDoItem.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }
    }

    @Override
    public void delete(ToDoItem toDoItem) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = createConnection();
            stmt = conn.prepareStatement("DELETE FROM todo_item " +
                    "WHERE id = ?");
            stmt.setLong(1, toDoItem.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
            closeStatement(stmt);
            closeResultSet(rs);
        }
    }
}
