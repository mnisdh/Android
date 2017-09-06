
public class StringApi {
	
	public void test(){
		String a = "String/Test";
		
		// 길이
		System.out.println(a.length());
		
		// 위치검색
		System.out.println(a.indexOf("T"));
		System.out.println(a.indexOf("Test"));
		
		// 특정 구분자로 분해
		String[] temp = a.split("/");
		for(String item : temp) System.out.println(item);
		
		// 빈문자열로 자르면 문자열을 글자 하나 단위로 분해
		String[] temp2 = a.split("");
		
		// 문자열 자르기
		// substring에서 인덱스는 문자열 앞으로 생각하고 사용
		System.out.println(a.substring(0, 6));
		
		// 문자열 바꾸기
		System.out.println(a.replace("Te", "Px"));
		
		// 특정 문자열로 시작되는지를 검사
		System.out.println(a.startsWith("Str"));
	}
	
	public void builderVsBuffer(){
		// jdk 1.5 버전 이상부터는 일반적인 스트링 연산을 StringBuilder로
		// 컴파일러가 자동변환 해준다.
		// 더해질때마다 새로운 메모리를 생성해서 더해줌
		String result = "안" + "녕하" + "세요" + "! 반갑습니다";
		
		// 속도가 빠름
		// 비동기에서 안정성이 보장된다
		StringBuffer buffer = new StringBuffer();
		buffer.append("안");
		buffer.append("녕하").append("세요");
		buffer.append("반갑습니다");
		System.out.println(buffer.toString());
		
		// 속도 가장빠름
		// 비동기에서 안정성이 보장되지 않는다
		// 다중 Thread 환경에서는 StringBuilder로 변수를 사용하지 않는다
		StringBuilder builder = new StringBuilder();
		builder.append("안");
		builder.append("녕하").append("세요");
		builder.append("반갑습니다");
		System.out.println(builder.toString());
	}
	
	public void StringConstantPool(){
		// 작년까지 permanent 영역이였으나 1.7이후 heap 영역으로 변경됨
		
		// 동일 문자열 추가시 재추가 없이 ConstantPool에서 값에대한 주소값만 갖고와서 사용됨
		String a = "안녕하세요";
		String b = "안녕하세요";
		
		System.out.println(a == b); //주소 체크
		System.out.println(a.equals(b)); // 값 체크
		
		// New 생성할때는 ConstantPool에 넣지 않음
		String c = new String("안녕하세요");
		
		String d = c.intern(); // New 로 생성한 객체를 ConstantPool에 넣음
	}
	
	
	
}
