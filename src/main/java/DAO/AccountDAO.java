package DAO;
import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;

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
}
