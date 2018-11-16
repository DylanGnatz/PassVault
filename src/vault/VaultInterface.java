package vault;

/*
  In addition the Vault must implement two constructors
  
	public Vault();
	public Vault(encrypt.Encryptor e);
	
  The vault must also implement the following method for
  debugging / testing purposes.  Note that this method
  has package-level visibility
  
  String getEncryptedPassword(String username, String sitename)
     throws UserNotFountException, SiteNotFoundException;
*/

public interface VaultInterface {

	/**
	 * Creates a new user account in the vault.
	 * 
	 * @param username a 6-12 lowercase letter String.
	 * @param password a 6-15 character String with at least one letter, one digit, 
	 * and one special character from '!@#$%^&'.
	 * @throws InvalidUsernameException
	 * @throws InvalidPasswordException
	 * @throws DuplicateUserException
	 */
	public void newUser(String username, String password)
			throws InvalidUsernameException, InvalidPasswordException, DuplicateUserException;

	/**
	 * Generates a new password for a specified website and stores it.
	 * 
	 * @param username of a user already created in the vault.
	 * @param password the user's password.
	 * @param sitename the name of the site to generate a password for.
	 * @return String the unencrypted site password
	 * @throws vault.DuplicateSiteException
	 * @throws vault.UserNotFoundException
	 * @throws UserLockedOutException
	 * @throws PasswordMismatchException
	 * @throws InvalidSiteException
	 */
	public String newSite(String username, String password, String sitename) throws vault.DuplicateSiteException,
			vault.UserNotFoundException, UserLockedOutException, PasswordMismatchException, InvalidSiteException;

	/**
	 * Generates and stores a new password for a site with a password already stored. 
	 * @param username of a user already created in the vault.
	 * @param password the user's password.
	 * @param sitename the name of a site that already has a stored password.
	 * @return String the unencrypted site password
	 * @throws SiteNotFoundException
	 * @throws UserNotFoundException
	 * @throws UserLockedOutException
	 * @throws PasswordMismatchException
	 */
	public String updateSite(String username, String password, String sitename)
			throws SiteNotFoundException, UserNotFoundException, UserLockedOutException, PasswordMismatchException;

	/**
	 * Retrieves the stored password for a site the user has already added.
	 * @param username of a user already created in the vault.
	 * @param password the user's password.
	 * @param sitename the name of a site that already has a stored password.
	 * @return String the unencrypted site password
	 * @throws SiteNotFoundException
	 * @throws UserNotFoundException
	 * @throws UserLockedOutException
	 * @throws PasswordMismatchException
	 */
	public String retrieveSite(String username, String password, String sitename)
			throws SiteNotFoundException, UserNotFoundException, UserLockedOutException, PasswordMismatchException;
}
