import java.util.Scanner;

/**
 * 출력 -> System.out.print
 * 입력 -> Scanner(System.in)
 * 
 * l - list : 목록 전체보기
 * c - create : 데이터 입력모드로 전환
 * r - read : 데이터 읽기모드로 전환
 * u - update : 데이터 수정모드로 전환
 * d - delete : 데이터 삭제모드로 전환
 * exit - 종료
 * 
 * @author daeho
 *
 */
public class MemoMain {
	
	public static void main(String[] args) {
		ModelWithDB model = new ModelWithDB();
		View view = new View();
		Control control = new Control();
		control.process(model, view);
	}
	
}




















