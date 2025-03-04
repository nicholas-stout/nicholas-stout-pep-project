package DAO;
import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAO {
    /**
     * This method will insert a new account into the database
     * @param account An Account object to be inserted
     * @return The new account that was created
     * TODO Maybe returning the generated account_id is unnecessary
     */
    public Account insertAccount(Account account) {
        // Create new connection
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if (pkeyResultSet.next()) {
                int generated_account_id = pkeyResultSet.getInt(1); 
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Method to get account by username and password
     * @param username username of the account
     * @param password password of the account
     * @return the account if it exists
     */
    public Account getAccountByUsernameAndPassword(String username, String password) {
        // Create new connection
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username=? AND password=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Account(resultSet.getInt("account_id"), resultSet.getString("username"), 
                    resultSet.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Method to get an account by its username. This is necessary for the Service layer to verify if an account already exists.
     * @param username a String representing the username of an account
     * @return the Account with that username if it exists, null otherwise.
     */
    public Account getAccountByUsername(String username) {
        // Get connection
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Account(resultSet.getInt("account_id"), resultSet.getString("username"), 
                    resultSet.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    /**
     * Method to get an Account by its id. This will be necessary for the Message service layer to verify if an account_id exists
     * @param id An int representing the account_id
     * @return The Account with the id if it exists, null otherwise.
     */
    public Account getAccountById(int account_id) {
        // Get connection
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE account_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Account(resultSet.getInt("account_id"), resultSet.getString("username"),
                    resultSet.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
}
