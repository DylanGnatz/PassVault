package vault;

import java.util.HashMap;



public class User {

	private static final int MAP_SIZE = 20;
	
	private String username;
	private String password;
	private int loginAttempts;
	private boolean userLocked;
	
	private HashMap <String, String> sites;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.userLocked = false;
		this.loginAttempts = 0;
		this.sites = new HashMap<>(MAP_SIZE);
	}
	
	public void addSite(String site, String pword) throws DuplicateSiteException, UserLockedOutException {
		checkUserLocked();
		if (getSites().containsKey(site))
			throw new DuplicateSiteException();
		getSites().put(site, pword);
	}
	
	public String getPass(String site) throws PasswordMismatchException, SiteNotFoundException, UserLockedOutException {
		checkUserLocked();
		if (!getSites().containsKey(site))
			throw new SiteNotFoundException();
		return getSites().get(site);
	}
	
	public void changePass(String site, String pword) throws SiteNotFoundException, UserLockedOutException {
		checkUserLocked();
		if (!getSites().containsKey(site))
			throw new SiteNotFoundException();
		getSites().replace(site, pword);
	}
	
	public boolean isCorrectPass(String pword) throws PasswordMismatchException, UserLockedOutException {
		if (this.getClass() != AdminUser.class && loginAttempts >= 3) {
				userLocked = true;
		}
		checkUserLocked();
		loginAttempts++;
		if (pword.equals(password)) {
			loginAttempts = 0;
			return true;
		}
		return false;		
	}
	
	protected void unlockUser(){
		this.loginAttempts = 0;
		this.userLocked = false;
	}
	
	private void checkUserLocked() throws UserLockedOutException {
		if (userLocked)
			throw new UserLockedOutException();
	}

	public HashMap <String, String> getSites() {
		return sites;
	}
}
	
//	public static void main(String[] args) {
//		System.out.println(isValidUser("tim"));
//		System.out.println(isValidUser("timtimtimtimtimtim"));
//		System.out.println(isValidUser("timTIm"));
//		System.out.println(isValidUser("timtim24"));
//		System.out.println(isValidUser("timtim!"));
//		System.out.println(isValidUser("timtim"));
//		System.out.println("Testing passes: ");
//		System.out.println(isValidPass("tom!2"));
//		System.out.println(isValidPass("01!!!!"));
//		System.out.println(isValidPass("timtim01"));
//		System.out.println(isValidPass("timtim!!!"));
//		System.out.println(isValidPass("HELLWORLD01!HELLO"));
//		System.out.println(isValidPass("timtim01#"));
//		System.out.println(isValidPass("HELLWORLD01$"));
//		
//	}


