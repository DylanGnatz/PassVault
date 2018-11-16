package vault;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestNewUser {
	
	@Test
	void testNewUser() throws InvalidPasswordException, DuplicateUserException, InvalidUsernameException {
		Vault v = new Vault();
		v.newUser("dgnatz", "1234a!");
		return;
	}
	


	@Test
	void testInvalidUserName() throws InvalidPasswordException, DuplicateUserException {
		Vault v = new Vault();
		try {
			v.newUser("bob", "1234a!");
		} catch (InvalidUsernameException e) {
			return;
		}
		fail("Allowed invalid username");
	}
	
	@Test
	void testInvalidPass() throws DuplicateUserException, InvalidUsernameException {
		Vault v = new Vault();
		try {
			v.newUser("dylangnatz", "weakpass");
		} catch (InvalidPasswordException e) {
			return;
		}
		fail("Allowed invalid password");
	}
	
	@Test
	void testDuplicateUser() throws InvalidUsernameException, InvalidPasswordException {
		Vault v = new Vault();
		try {
			v.newUser("dylangnatz", "1234a!");
			v.newUser("dylangnatz", "12345a!");
		} catch (DuplicateUserException e) {
			return;
		}
		fail("Allowed duplicate user");
	}

}
