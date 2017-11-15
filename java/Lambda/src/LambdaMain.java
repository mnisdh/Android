
public class LambdaMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		LambdaInterfaceBasic lib = new LambdaInterfaceBasic();
		lib.run();
		
		
		
//		Process p = new Process();
//		
//		// 참조형 -> 로직이 실행되는 측의 인자와 리턴되는 인자를 매핑할 수 있다
//		//One o = s -> s = "Hello~";
//		//p.process1(System.out::prinln);
//		
//		p.process((x,y,z) -> {
//			System.out.println("Hello~");
//			return "";
//		});
	}

}

class Process{
	public void process(Two two){
		two.run(1, 2, 3);
	}
	public void process1(One one){
		
	}
}

// 1. 하나의 클래스에 하나의 함수 ( O )
interface One{
	public String run();
}

interface Two{
	public String run(int x, double y, int z);
}