
public class TestMain {

	public static void main(String[] args) {
		System.out.println(test("a"));
	}

	public static boolean test(String inputString){
		String tempStr = new StringBuffer(inputString).reverse().toString();
		
		return inputString.equals(tempStr);
	}

}
