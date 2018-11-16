package vault;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestRetrievePassword {

	@Test
	void testRetrievePass() throws InvalidUsernameException, InvalidPasswordException, DuplicateUserException, DuplicateSiteException, UserNotFoundException, PasswordMismatchException, InvalidSiteException, UserLockedOutException, SiteNotFoundException {
		String pw;
		Vault v = new Vault();
		v.newUser("dgnatz", "1234a!");
		pw = v.newSite("dgnatz", "1234a!", "amazon");
		assertEquals(pw, v.retrieveSite("dgnatz", "1234a!", "amazon"));
	}
	
	@Test
	void testSiteNameNotFound() throws InvalidUsernameException, InvalidPasswordException, DuplicateUserException, DuplicateSiteException, UserNotFoundException, PasswordMismatchException, InvalidSiteException, UserLockedOutException {
		Vault v = new Vault();
		v.newUser("dgnatz", "1234a!");
		try {
			v.retrieveSite("dgnatz", "1234a!", "amazon");
		} catch (SiteNotFoundException e) {
			return;
		}
		fail("Allowed retrieval of site name not in vault.");
	}
}
