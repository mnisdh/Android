/**
 * 클래스 설명
 * @author daeho
 *
 */
public class Sub {
	/**
	 * 메소드 설명
	 */
	public void test(){
		//정수 연산은 int로 대체된다
		byte a = 10;
		byte b = 11;
		
		//byte c = a + b; (x)
		//int c = a + b; (o)
		byte c = (byte) (a + b);
		
		//--------------------------------------------
		
		//long 선언시 값마지막에 L붙여줌
		long longA = 213984342L;
		
		//--------------------------------------------
		
		//실수는 모두 double 로 대체된다
		double dVal = 2.55556;
		//float일 경우 값마지막에 f붙여줌
		float fVal = 2.55556f;
		
		//--------------------------------------------
		
		//실수는 직접연산하지 않는다
		float d = 3.14f;
		float e = 4.1928f;
		//float f = d + e;  (x)
		float f = Float.sum(d, e);
		
		double g = 5.223;
		double h = 1.98343;
		double i = Double.sum(g, h);
		
		//--------------------------------------------
		
		changeStringToInteger("123");
		
		//연산자 우선순위는 괄호로 하고 부족한부분은 검색
		int j = (30 + 1) * 2 / (50 - 3);
	}
	
	/**
	 * 숫자를 문자로 변환
	 * @param number
	 * @return
	 */
	public String changeNumberToString(int number){
		return number + ""; 
	}
	
	/**
	 * 숫자인 문자열을 숫자로 변환
	 * @param word
	 * @return
	 */
	public int changeStringToInteger(String word){
		return Integer.parseInt(word);
	}
	
	/**
	 * long숫자인 문자열을 long으로 변환
	 * @param word
	 * @return
	 */
	public long changeStringToLong(String word){
		return Long.parseLong(word);
	}

}
