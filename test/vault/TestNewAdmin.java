package vault;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestNewAdmin {

	@Test
	void testNewAdmin() throws InvalidPasswordException, DuplicateUserException, InvalidUsernameException, PasswordMismatchException {
		Vault v = new Vault();
		v.newAdmin("dgnatz", "1234a!", "123456789abc!");
		return;
	}
	
	@Test
	void testControlPasswordMismatch() throws InvalidPasswordException, DuplicateUserException, InvalidUsernameException, PasswordMismatchException {
		Vault v = new Vault();
		try {
			v.newAdmin("dgnatz", "1234a!", "123456789abC!");
		} catch (PasswordMismatchException e) {
			return;
		}
		fail("Allowed creation of admin with incorrect control password.");
	}

}
