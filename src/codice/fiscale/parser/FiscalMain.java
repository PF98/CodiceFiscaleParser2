package codice.fiscale.parser;

public class FiscalMain {

	public static final String CODE = "rssndr98c91b123z";
	
	public static void main(String[] args) {
		Parser p = new Parser();
		System.out.println(p.checkCode(CODE));
	}
	
	
}
