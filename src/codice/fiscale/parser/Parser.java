package codice.fiscale.parser;

public class Parser {
	private static final int[] NUM_POSITIONS = {6, 7, 9, 10, 12, 13, 14};
	private static final int[] CHAR_POSITIONS = {0, 1, 2, 3, 4, 5, 8, 11, 15};
	private static final char[] MONTH_LETTERS = {'a','b','c','d','e','h','l','m','p','r','s','t'};
	private static final int[] MONTH_DAYS = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private static final int[] DAY_POSITIONS = {9, 10};
	
	public int checkType(char c) {
		if (Character.isDigit(c))
			return 1;
		if (Character.toUpperCase(c) <= 'Z' || Character.toUpperCase(c) >= 'A')
			return 2;
		return 0;
	}
	
	public int checkStringType(String s) {
		int out = -1;
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (out == -1)
				out = checkType(ch);
			else if (out != checkType(ch))
				return 0;
			
			if (out == 0)
				return 0;
		}
		
		return out;
	}
	
	public boolean checkDay(String s, int month) {
		if (checkStringType(s) != 1)
			return false;
		int val = 0;
		for (int i = 0; i < s.length(); i++) {
			val += (int) (s.charAt(i) - '0') * (int) Math.pow(10, s.length() - i - 1);
		}
		
		if (!checkGender(s)) { // female
			val -= 40;
		}
		
		if (val > MONTH_DAYS[month] || val < 1)
			return false;
		
		return true;
	}
	
	public boolean checkGender(String s) {
		int val = 0;
		for (int i = 0; i < s.length(); i++) {
			val += (int) (s.charAt(i) - '0') * (int) Math.pow(10, s.length() - i - 1);
		}
		if (val < 40)
			return true;
		return false;
	}
	
	public boolean checkCode(String code) {
		if (code.length() != 16)
			return false;
		
		for (int num : NUM_POSITIONS) {
			if (checkType(code.charAt(num)) != 1)
				return false;
		}
		
		for (int num : CHAR_POSITIONS) {
			if (checkType(code.charAt(num)) != 2)
				return false;
		}
		
		int month = -1;
		boolean valid = false;
		for (int i = 0; i < MONTH_LETTERS.length; i++) {
			if (MONTH_LETTERS[i] == Character.toLowerCase(code.charAt(8))) {
				month = i;
				valid = true;
			}
		}
		if (!valid)
			return false;
		
		
		
		StringBuffer day = new StringBuffer();
		for (int dayPos : DAY_POSITIONS) {
			day.append(code.charAt(dayPos));
		}
		if (!checkDay(day.toString(), month))
			return false;
		
		
		return true;
	}
}
