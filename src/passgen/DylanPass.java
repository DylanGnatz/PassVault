package passgen;

import java.util.Random;

public class DylanPass implements PassGen {
	private static final int NUM_LETTERS = 7;
	private static final int INT_RANGE = 1000;
	private static final String SPECIAL_CHAR = "!@#$%^&";

	@Override
	public String generatePass() {
		StringBuilder sb = new StringBuilder();
		sb.append(generateRandomWord());
		sb.append(generateIntString());
		sb.append(generateSpecCharString());
		return sb.toString();
	}
	
	private static String generateRandomWord() {
	    String randomString;
	    Random random = new Random();
	   
        char[] word = new char[NUM_LETTERS]; 
        for(int j = 0; j < word.length; j++) {
            word[j] = (char)('a' + random.nextInt(26));
        }
        randomString = new String(word);
	    return randomString;
	}
	
	private String generateIntString() {
		Random random = new Random();
		return Integer.toString(random.nextInt(INT_RANGE));
	}
	
	private String generateSpecCharString() {
		Random random = new Random();
		return Character.toString(SPECIAL_CHAR.charAt(random.nextInt(SPECIAL_CHAR.length())));
	}

	

}
