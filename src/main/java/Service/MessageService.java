package Service;

import DAO.MessageDAO;
import Model.Message;
import DAO.AccountDAO;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO = new AccountDAO();
}
