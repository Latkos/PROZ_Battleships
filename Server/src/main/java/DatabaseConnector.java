import org.jasypt.util.password.StrongPasswordEncryptor;

import java.sql.*;

/**
 * The type Database connector.
 */
public class DatabaseConnector {
    /**
     * The Connection.
     */
    Connection connection;
    /**
     * The Encryptor.
     */
    StrongPasswordEncryptor encryptor;

    /**
     * Instantiates a new Database connector.
     */
    public DatabaseConnector() {
        String url = "jdbc:sqlite:battleships_database.db";
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        encryptor = new StrongPasswordEncryptor();
    }

    /**
     * Close connection.
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Hash password string.
     *
     * @param password the password
     * @return the string
     */
    public String hashPassword(String password) {
        return encryptor.encryptPassword(password);
    }

    private boolean checkForUsername(String username) {
        String query = "SELECT Username FROM Users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                if (resultSet.getString("Username").equals(username))
                    return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private void createUser(String username, String password) throws SQLException {
        String query = "INSERT INTO Users(Username,Password) VALUES(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();
    }

    private boolean checkForPasswordCorrectness(String username, String password) throws SQLException {
        String query = "SELECT Password FROM Users WHERE Username=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        ResultSet rs = preparedStatement.executeQuery();
        String realPassword = rs.getString("Password");
        return encryptor.checkPassword(password, realPassword);
    }

    /**
     * Login user boolean.
     *
     * @param username the username
     * @param password the password
     * @return the boolean
     * @throws SQLException the sql exception
     */
    public boolean loginUser(String username, String password) throws SQLException {
        if (username.isEmpty() || password.isEmpty())
            return false;
        String oldPassword = password;
        if (!checkForUsername(username)) {
            password = hashPassword(password);
            createUser(username, password);
        }
        return checkForPasswordCorrectness(username, oldPassword);
    }
}
