package encrypt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestConstructor {

	@Test
	void testNegativeOffset() {
		try {
			Encryptor e1 = new CaesarCypher(-1);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail ("allowed negative offset value.");
		
	}
	
	@Test
	void testOffsetTooLarge() {
		try {
			Encryptor e1 = new CaesarCypher(2000);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail ("allowed offset value greater than the number of chars.");
	}

}
