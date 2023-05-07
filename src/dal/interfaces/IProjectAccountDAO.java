package dal.interfaces;

import java.util.List;

public interface IProjectAccountDAO {

    /**
     * Gets all the projects for a certain account.
     * @param accountID ID of the account.
     * @return A String list of reference numbers.
     */
    List<String> getAllProjectsForAccount(int accountID);

    /**
     * Gets all the accounts for the selected project.
     * @param refNumber Reference number of the project
     * @return An Integer list of accounts.
     */
    List<Integer> getAllAccountsForProject(String refNumber);

    /**
     * Saves the connection between a project and an account.
     * @param refNumber Reference number of the project.
     * @param accountID ID of the account.
     */
    void saveProject(String refNumber, int accountID);

    /**
     * Deletes the connection between an account and a project.
     * @param refNumber
     * @param accountID
     */
    void deleteConnection(String refNumber, int accountID);

}
