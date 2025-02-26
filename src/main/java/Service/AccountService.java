package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    /**
     * Default no-args constructor will initialize the AccountDAO object
     */
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor to be used when an AccountDAO object is provided
     * @param accountDAO an AccountDAO object to be used for the AccountService object
     */
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * TODO Add logic
     * Method to persist an Account to the database. It will first check if the account's username and password meet our requirements.
     * If the account information is not valid, it will return null.
     * @param account an Account to be persisted to the database. An account_id is not necessary
     * @return the account that was persisted to the database, if successful. This object will have an account_id
     */
    public Account addAccount(Account account) {
        return null;
    }

    /**
     * TODO Add logic
     * Method to verify if an Account can be added to the database
     * @param account an Account object to be validated
     * @return true or false based on whether the account can be added to the database
     */
    private boolean canBeAdded(Account account) {
        return false;
    }

    /**
     * TODO Add logic
     * Method to verify if a username is valid. A username is valid if it is not blank and not used by another user in the database.
     * @param username a String representing the username to be validated
     * @return true or false depending on whether the username is valid
     */
    private boolean isValidUsername(String username) {
        return false;
    }

    /**
     * TODO Add logic
     * Method to verify if a password is valid. A password is valid if it is at least 4 characters long.
     * @param password a String representing the password to be validated
     * @return true or false depending on whether the password is valid
     */
    private boolean isValidPassword(String password) {
        return false;
    }

}
