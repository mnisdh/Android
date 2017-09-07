import java.util.ArrayList;
import java.util.Scanner;

/**
 * 화면 입출력을 관리하는 클래스
 * 
 * @author daeho
 *
 */
public class View {
	private Scanner scanner;
	
	/**
	 * 생성자 스캐너 생성
	 */
	View(){
		// 입력을 받아서 처리하는 도구
		scanner = new Scanner(System.in);
	}
	
	public Scanner getScanner(){
		return scanner;
	}
	
	/**
	 * list 저장소를 반복문을 돌면서 한줄씩 출력
	 */
	public void showList(ArrayList<Memo> list){
		for(Memo memo : list){
			print("No : " + memo.getNo());
			print(" | Author : " + memo.getName());
			println(" | Content : " + memo.getContent());
		}
	}
	
	/**
	 * 키보드 입력을 받는 함수
	 */
	public Memo create(){
		println("이름을 입력하세요 : ");
		String name = scanner.nextLine();
		println("내용을 입력하세요 : ");
		String content = scanner.nextLine();
		
		println("-------- 입력결과 --------");
		println("이름 : " + name);
		println("내용 : " + content);
		
		return new Memo(name, content);
	}
	
	/**
	 * 데이터 읽기
	 * @param scanner
	 */
	public void read(Memo memo){
	 	println("-------- " + memo.getNo() + " --------");
	 	println("날짜 : " + memo.getDatetime());
	 	println("이름 : " + memo.getName());
		println("내용 : " + memo.getContent());
	}
	
	/**
	 * 데이터 업데이트
	 * @param scanner
	 */
	public void update(Memo memo){
		println("이름을 입력하세요 : ");
		String name = scanner.nextLine();
		println("내용을 입력하세요 : ");
		String content = scanner.nextLine();
		
		memo.update(name, content);
		
		println("-------- 입력결과 --------");
		println("이름 : " + name);
		println("내용 : " + content);
	}
	
	/**
	 * 데이터 삭제
	 * @param scanner
	 */
	public void delete(){
		println("삭제되었습니다.");
	}
	
	/**
	 * 불러올 번호 입력받기
	 * @param scanner
	 * @return
	 */
	public int getNo(){
		println("불러올 번호를 입력하세요 : ");
		String sNo = scanner.nextLine();
		
		int iNo = -1;
		
		// 입력받은 내용을 integer로 변환안될시 -1로 입력
		try{
			iNo = Integer.parseInt(sNo);
		}catch(Exception ex){
			iNo = -1;
		}
		
		return iNo;
	}
	
	/**
	 * 문자열 출력
	 * @param string
	 */
	public void print(String string){
		System.out.print(string);
	}
	
	/**
	 * 문자열 출력
	 * @param string
	 */
	public void println(String string){
		System.out.println(string);
	}
}

