package vault;

import java.util.HashMap;

import encrypt.CaesarCypher;
import encrypt.Encryptor;
import passgen.DylanPass;
import passgen.PassGen;

/**
 * @author dylangnatz
 *
 */
public class Vault implements VaultInterface {
	private static final int MAP_SIZE = 20;
	private static final String SPECIAL_CHAR = "!@#$%^&";
	private static final int MIN_LENGTH_U = 6;
	private static final int MAX_LENGTH_U = 12;
	private static final int MIN_LENGTH_P = 6;
	private static final int MAX_LENGTH_P = 15;
	private static final Encryptor DEF_ENCRYPTOR = new CaesarCypher();
	private static final PassGen PASS_GEN = new DylanPass();
	private static final String CONTROL_PASS = DEF_ENCRYPTOR.encrypt("123456789abc!");

	private HashMap <String, User> users;
	private Encryptor e;
	private PassGen pg;
	
	/**
	 * Constructor for the Vault class
	 * 
	 * @param e an encryptor
	 */
	public Vault(Encryptor e) {
		users = new HashMap<>(MAP_SIZE);
		this.e = e;
		this.pg = PASS_GEN;
	}
	
	
	/**
	 * Default constructor for the Vault class
	 */
	public Vault() {
		this(DEF_ENCRYPTOR);
	}

	@Override
	public void newUser(String username, String password)
			throws InvalidUsernameException, InvalidPasswordException, DuplicateUserException {
		if (!isNewUser(username))
			throw new DuplicateUserException();
		if (!isValidUser(username)) 
			throw new InvalidUsernameException();
		else if (!isValidPass(password))
			throw new InvalidPasswordException();
		users.put(username, new User(username, e.encrypt(password)));
	}

	
	@Override
	public String newSite(String username, String password, String sitename) throws DuplicateSiteException,
			UserNotFoundException, PasswordMismatchException, InvalidSiteException, UserLockedOutException {
		String spass;
		User u = retrieveUser(username, password);
		if (!isValidSite(sitename))
			throw new InvalidSiteException();
		spass = pg.generatePass();
		u.addSite(sitename, e.encrypt(spass));
		return spass;
	}

	@Override
	public String updateSite(String username, String password, String sitename)
			throws SiteNotFoundException, UserNotFoundException, UserLockedOutException, PasswordMismatchException {
		String spass;
		User u = retrieveUser(username, password);
		spass = pg.generatePass();
		u.changePass(sitename, e.encrypt(spass));
		return spass;
	}

	@Override
	public String retrieveSite(String username, String password, String sitename)
			throws SiteNotFoundException, UserNotFoundException, UserLockedOutException, PasswordMismatchException {
		User u = retrieveUser(username, password);
		return e.decrypt(u.getPass(sitename));
	}
	
	
	/**
	 * Creates a new AdminUser account.
	 * @param username the desired username
	 * @param password the desired password
	 * @param controlPass the control password for the vault.
	 * @throws InvalidUsernameException
	 * @throws InvalidPasswordException
	 * @throws DuplicateUserException
	 * @throws PasswordMismatchException
	 */
	public void newAdmin(String username, String password, String controlPass) 
			throws InvalidUsernameException, InvalidPasswordException, DuplicateUserException, PasswordMismatchException {
		if (!isNewUser(username))
			throw new DuplicateUserException();
		else if (!isValidUser(username)) 
			throw new InvalidUsernameException();
		else if (!isValidPass(password))
			throw new InvalidPasswordException();
		else if (!isControlPass(controlPass))
			throw new PasswordMismatchException();
		users.put(username, new AdminUser(username, e.encrypt(password)));
	}
	
	/**
	 * Sets a User account's locked status to unlocked.
	 * @param adminUsername the username of an admin account.
	 * @param adminPassword the password for the admin account.
	 * @param username the username of the account to unlock.
	 * @throws PermissionsDeniedException
	 * @throws UserNotFoundException
	 * @throws UserLockedOutException
	 * @throws PasswordMismatchException
	 */
	public void unlockUser(String adminUsername, String adminPassword, String username) throws PermissionsDeniedException, UserNotFoundException, UserLockedOutException, PasswordMismatchException {
		if (!users.containsKey(adminUsername))
			throw new UserNotFoundException();
		else if (!users.containsKey(username))
			throw new UserNotFoundException();
		else if (!(users.get(adminUsername).getClass() == AdminUser.class))
			throw new PermissionsDeniedException();
		AdminUser a = (AdminUser) retrieveUser(adminUsername, adminPassword);
		User u = users.get(username);
		a.UnlockUser(u);
	}
	
	 /**
	 * Retrieves a user's encrypted password for a given site. 
	 * @param String username
	 * @param String sitename
	 * @return String the user's encrypted password for the given site.
	 * @throws UserNotFoundException
	 * @throws SiteNotFoundException
	 */
	String getEncryptedPassword(String username, String sitename)
		     throws UserNotFoundException, SiteNotFoundException{
		 if (!users.containsKey(username))
			 throw new UserNotFoundException();
		 else if (!(users.get(username).getSites().containsKey(sitename)))
		 	throw new SiteNotFoundException();
		 return users.get(username).getSites().get(sitename);
	 }
	
	/**
	 * Evaluates if the provided password is the same as the control password.
	 * @param String pword 
	 * @return boolean if the password is equal to the control password. 
	 */
	private boolean isControlPass(String pword) {
		return e.encrypt(pword).equals(CONTROL_PASS);
	}
	
	
	/**
	 * Retrieves a user by their username and password.
	 * @param String uname 
	 * @param String pword
	 * @return User
	 * @throws UserNotFoundException
	 * @throws UserLockedOutException
	 * @throws PasswordMismatchException
	 */
	private User retrieveUser(String uname, String pword) throws UserNotFoundException, UserLockedOutException, PasswordMismatchException {
		User u;
		String ePass = e.encrypt(pword);
		if (!users.containsKey(uname))
			throw new UserNotFoundException();
		u = users.get(uname);
		if (!u.isCorrectPass(ePass))
			throw new PasswordMismatchException();
		return u;
		
	}
	
	/**
	 * Evaluates if the User already has an account in the Vault.
	 * @param String uname
	 * @return boolean
	 */
	private boolean isNewUser(String uname) {
		return !(users.containsKey(uname));
	}
	
	/**
	 * Evaluates if the sitename is valid.
	 * @param String sname
	 * @return boolean
	 */
	
	private static boolean isValidSite(String sname) {
		return isValidUser(sname);
	}
	
	/**
	 * Evaluates if the username is valid.
	 * @param String uname
	 * @return boolean
	 */
	private static boolean isValidUser(String uname){
		if (uname.length() < MIN_LENGTH_U || uname.length() > MAX_LENGTH_U) {
			return false;
		}
		for (char c: uname.toCharArray()) {
			if (!Character.isLowerCase(c))
				return false;
		}
		return true;
		
	}
	
	/** 
	 * Evaluates if the password is valid.
	 * @param String pword
	 * @return boolean
	 */
	private static boolean isValidPass(String pword){
		boolean hasL = false;
		boolean hasD = false;
		boolean hasS = false;
		
		if (pword.length() < MIN_LENGTH_P|| pword.length() > MAX_LENGTH_P ) {
			return false;
		}
		for (char c: pword.toCharArray()) {
			if (Character.isDigit(c))
				hasD = true;
			else if (Character.isLetter(c))
				hasL = true;
			else if (isSpecialChar(c))
				hasS = true;	
		}
		if (hasL && hasD && hasS)
			return true;
		return false;
	}
	
	/**
	 * Evaluates if the char is one of the specified special chars.
	 * @param char c
	 * @return boolean
	 */
	private static boolean isSpecialChar(char c) {
		for (char s: SPECIAL_CHAR.toCharArray()) {
			if (c == s)
				return true;
		}
		return false;
	}
	
}
