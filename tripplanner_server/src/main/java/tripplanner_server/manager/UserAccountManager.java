/**
 * 
 */
package tripplanner_server.manager;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import tripplanner_server.models.UserAccount;

/**
 * @author MADHAVAN001
 *
 */
public class UserAccountManager {
	static Set<UserAccount> userAccounts = new HashSet<UserAccount>();
	DatabaseManager manager;

	public UserAccountManager() {
		manager = new DatabaseManager();
	}

	/**
	 * 
	 * @param userName
	 * @param email
	 * @param password
	 */
	public void addAccount(String userName, String email, String password) {
		Date date = new Date();
		UserAccount account = new UserAccount(email, password, date, userName);
		manager.addUserAccount(account);
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public boolean checkAccount(String email) {
		if (email == null || email.length() == 0)
			return false;
		return manager.checkAccount(email);
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public int getAccountId(String email) {
		if (email == null || email.length() == 0) {
			return -1;
		}
		return manager.getAccountId(email);
	}
}
