package vault;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestUpdateSite {

	@Test
	void testNewSite() throws InvalidUsernameException, InvalidPasswordException, DuplicateUserException, DuplicateSiteException, UserNotFoundException, PasswordMismatchException, InvalidSiteException, UserLockedOutException, SiteNotFoundException {
		String pw1, pw2;
		Vault v = new Vault();
		v.newUser("dgnatz", "1234a!");
		pw1 = v.newSite("dgnatz", "1234a!", "amazon");
		pw2 = v.updateSite("dgnatz", "1234a!", "amazon");
		assertNotEquals(pw1, v.retrieveSite("dgnatz", "1234a!", "amazon"));
		assertEquals(pw2, v.retrieveSite("dgnatz", "1234a!", "amazon"));
	}
}
