package encrypt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestEncryptDecrypt {

	@Test
	void testEncryptDecrypt() {
		String testString, encryptedString;
		Encryptor e = new CaesarCypher();
		testString = "HeLLO wOrLD!@#";
		encryptedString = e.encrypt(testString);
		assertEquals(testString, e.decrypt(encryptedString));
	}

}
