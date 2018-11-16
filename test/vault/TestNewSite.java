package vault;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestNewSite {

	@Test
	void testNewSite() throws InvalidUsernameException, InvalidPasswordException, DuplicateUserException, DuplicateSiteException, UserNotFoundException, PasswordMismatchException, InvalidSiteException, UserLockedOutException {
		Vault v = new Vault();
		v.newUser("dgnatz", "1234a!");
		v.newSite("dgnatz", "1234a!", "amazon");
		return;
	}
	
	@Test
	void testDuplicateSiteName() throws InvalidUsernameException, InvalidPasswordException, DuplicateUserException, InvalidSiteException, UserNotFoundException, PasswordMismatchException, InvalidSiteException, UserLockedOutException {
		Vault v = new Vault();
		v.newUser("dgnatz", "1234a!");
		try {
			v.newSite("dgnatz", "1234a!", "amazon");
			v.newSite("dgnatz", "1234a!", "amazon");
		} catch (DuplicateSiteException e) {
			return;
		}
		fail("Allowed duplicate site.");
	}
	
	@Test
	void testInvalidSiteName() throws InvalidUsernameException, InvalidPasswordException, DuplicateUserException, DuplicateSiteException, UserNotFoundException, PasswordMismatchException, InvalidSiteException, UserLockedOutException {
		Vault v = new Vault();
		v.newUser("dgnatz", "1234a!");
		try {
			v.newSite("dgnatz", "1234a!", "amaz0n");
		} catch (InvalidSiteException e) {
			return;
		}
		fail("Allowed invalid site name.");
	}
	
	@Test
	void testUserNotFound() throws InvalidUsernameException, InvalidPasswordException, DuplicateUserException, DuplicateSiteException, InvalidSiteException, PasswordMismatchException, InvalidSiteException, UserLockedOutException {
		Vault v = new Vault();
		v.newUser("dgnatz", "1234a!");
		try {
			v.newSite("gnatzd", "1234a!", "amazon");
		} catch (UserNotFoundException e) {
			return;
		}
		fail("Allowed username not in vault.");
	}
	
	@Test
	void testPasswordMismatch() throws InvalidUsernameException, InvalidPasswordException, DuplicateUserException, DuplicateSiteException, InvalidSiteException, UserNotFoundException, InvalidSiteException, UserLockedOutException {
		Vault v = new Vault();
		v.newUser("dgnatz", "1234a!");
		try {
			v.newSite("dgnatz", "1234b!", "amazon");
		} catch (PasswordMismatchException e) {
			return;
		}
		fail("Allowed password mismatch.");
	}
	
	@Test
	void testUserLockedOut() throws InvalidUsernameException, InvalidPasswordException, DuplicateUserException, DuplicateSiteException, InvalidSiteException, UserNotFoundException, InvalidSiteException, UserLockedOutException, PasswordMismatchException {
		Vault v = new Vault();
		v.newUser("dgnatz", "1234a!");
		try {
			v.newSite("dgnatz", "1234b!", "amazon");
		} catch (PasswordMismatchException e1) {
			try {
				v.newSite("dgnatz", "1234b!", "amazon");
			} catch (PasswordMismatchException e2) {
				try {
					v.newSite("dgnatz", "1234b!", "amazon");
					} catch (PasswordMismatchException e3) {
						try {
							v.newSite("dgnatz", "1234a!", "amazon");
						} catch (UserLockedOutException e4) {
							return;
						}
						
				}
			}
		}
		fail("Allowed locked out user to add site.");
	}

}
