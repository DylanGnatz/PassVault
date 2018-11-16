package encrypt;
public class CaesarCypher implements Encryptor {

	private static final String DEFAULT_CHARS = 
			"abcdefghijklmnopqrstuvwxyz" + 
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ" + 
			"0123456789" + 
			"., !@#$%^&";

	private String chars;
	private int offset;

	public CaesarCypher(String chars, int offset) {
		this.chars = chars;
		if (offset < 0 || offset >= chars.length()) {
			throw new IllegalArgumentException(
					"Bad offset " + offset + " must be between 0 and " + (chars.length() - 1));
		}
		this.offset = offset;
	}

	public CaesarCypher(int offset) {
		this(DEFAULT_CHARS, offset);
	}

	public CaesarCypher() {
		this(DEFAULT_CHARS, (int) (DEFAULT_CHARS.length() / 2));
	}

	public String encrypt(String s) {
		StringBuilder sb = new StringBuilder();
		for (char c : s.toCharArray()) {
			int indx = chars.indexOf(c);
			if (indx < 0)
				throw new IllegalArgumentException("String to be encrypted has unrecognized character " + c);
			int cpos = indx + offset;
			if (cpos >= chars.length())
				cpos = cpos - chars.length();
			sb.append(chars.charAt(cpos));
		}
		return sb.toString();
	}

	public String decrypt(String s) {
		StringBuilder sb = new StringBuilder();
		for (char c: s.toCharArray()) {
			int indx = chars.indexOf(c);
			if (indx < 0)
				throw new IllegalArgumentException("String to be encrypted has unrecognized character " + c);
			int cpos = indx - offset;
			if (cpos < 0)
				cpos = cpos + chars.length();
			sb.append(chars.charAt(cpos));
		}
		return sb.toString();
	}
}
