package vault;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestUnlockUser {

	@Test
	void testUnlockUser() throws InvalidUsernameException, InvalidPasswordException, DuplicateUserException, DuplicateSiteException, InvalidSiteException, UserNotFoundException, InvalidSiteException, UserLockedOutException, PasswordMismatchException, PermissionsDeniedException, SiteNotFoundException {
		Vault v = new Vault();
		v.newUser("dgnatz", "1234b!");
		v.newAdmin("gnatzd", "123456a$", "123456789abc!");
		v.newSite("dgnatz", "1234b!", "amazon");
		try {
			v.newSite("dgnatz", "1234a!", "zappos");
		} catch (PasswordMismatchException e1) {
			try {
				v.newSite("dgnatz", "1234a!", "zappos");
			} catch (PasswordMismatchException e2) {
				try {
					v.newSite("dgnatz", "1234a!", "zappos");
					} catch (PasswordMismatchException e3) {
						try {
							v.retrieveSite("dgnatz", "1234b!", "amazon");
						} catch (UserLockedOutException e4) {
							v.unlockUser("gnatzd", "123456a$", "dgnatz");
							v.retrieveSite("dgnatz", "1234b!", "amazon");
							return;
						}
						
				} 
			}
		}
		fail("Did not unlock user.");
	}
}
