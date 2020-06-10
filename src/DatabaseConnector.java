import java.sql.*;

public class DatabaseConnector {
    Connection connection;

    public DatabaseConnector() {
        String url = "jdbc:sqlite:battleships_database.db";

        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
        preparedStatement.setString(2, username);
        preparedStatement.executeUpdate();
    }

    private boolean checkForPasswordCorrectness(String username, String password) throws SQLException {
        String query = "SELECT Password FROM Users WHERE Username=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        ResultSet rs = preparedStatement.executeQuery();
        String realPassword = rs.getString("Password");
        return password.equals(realPassword);
    }

    public boolean loginUser(String username, String password) throws SQLException {
        if (!checkForUsername(username)) {
            createUser(username, password);
        }
        return checkForPasswordCorrectness(username, password);
    }
}
