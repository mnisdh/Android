# 텍스트 저장하는 코드 연습
	MVC 패턴을 사용한 텍스트 저장 코드

## MVC 패턴
	Model - 데이터를 의미
	View - 사용자가 보는 것을 의미(입/출력)
	Controller - 사용자가 보기 위해 데이터를 가공, 혹은 조작 하는 것을 의미(계산메소드등)

## 적용 코드
#### MemoMain(main class)  
```java
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	Model model = new Model();
	View view = new View();

	public static void main(String[] args) {
		MemoMain mMain = new MemoMain();

		// view의 scanner 불러다가 사용
		Scanner scanner = mMain.view.getScanner();

		String command = "";
		while(!command.equals("exit")){

			mMain.view.println("--------------- 명령어를 입력하세요 ---------------");
			mMain.view.println("전체목록(l), 추가(c), 읽기(r), 수정(u), 삭제(d)");
			mMain.view.println("exit - 종료");
			mMain.view.println("----------------------------------------------");

			command = scanner.nextLine(); // 엔터키가 입력될때까지 대기

			// 명령어 분기처리
			switch(command){
			case "l":
				mMain.view.showList(mMain.model.list);
				break;

			case "c":
				Memo cMemo = mMain.view.create();
				mMain.model.create(cMemo);
				break;

			case "r":
				int rNo = mMain.view.getNo();
				Memo rMemo = mMain.model.getMemo(rNo);
				if(rMemo != null) mMain.view.read(rMemo);
				else mMain.view.println("없는 번호입니다.");
				break;

			case "u":
				int uNo = mMain.view.getNo();
				Memo uMemo = mMain.model.getMemo(uNo);
				if(uMemo != null) mMain.view.update(uMemo);
				else mMain.view.println("없는 번호입니다.");
				break;

			case "d":
				int dNo = mMain.view.getNo();
				Memo dMemo = mMain.model.getMemo(dNo);
				if(dMemo != null) {
					mMain.model.delete(dMemo);
					mMain.view.delete();
				}
				else mMain.view.println("없는 번호입니다.");
				break;
			}
		}

		mMain.view.println("시스템이 종료되었습니다.");
	}

}
```

#### Model(model class)
```java
/**
 * 데이터를 저장하는 저장소를 관리하는 클래스
 * @author daeho
 *
 */
class Model{
	// 전체 메모를 저장하는 저장소
	ArrayList<Memo> list = new ArrayList<>();
	int count = 0;

	/**
	 * list에 memo 추가
	 * @param memo
	 */
	public void create(Memo memo){
		memo.setNo(++count);
		list.add(memo);
	}

	/**
	 * list의 memo 삭제
	 * @param memo
	 */
	public void delete(Memo memo){
		list.remove(memo);
	}

	/**
	 * list에서 no같은 memo 가져오기
	 * @param no
	 * @return
	 */
	public Memo getMemo(int no){
		// iNo와 같은 값을 가진 Memo의 list index를 반환
		for(int i = 0; i < list.size(); i++){
			Memo memo = list.get(i);
			if(memo.getNo() == no) return memo;
		}

		return null;
	}
}
```

#### View(view class)
```java
/**
 * 화면 입출력을 관리하는 클래스
 * @author daeho
 *
 */
class View{
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
```

#### Memo(개별 메모를 저장하는 class)
```java
/**
 * 개별글 한개 한개를 저장하는 클래스
 *
 * @author daeho
 *
 */
class Memo{
	private int no;
	private String name;
	private String content;
	private long datetime;

	/**
	 * 생성자
	 * @param name
	 * @param content
	 */
	Memo(String name, String content){
		this.name = name;
		this.content = content;
		this.datetime = System.currentTimeMillis();
	}

	/**
	 * no 설정하기
	 * @param no
	 */
	public void setNo(int no) {this.no = no;}
	/**
	 * no 가져오기
	 * @return
	 */
	public int getNo() {return no;}
	/**
	 * name 가져오기
	 * @return
	 */
	public String getName() {return name;}
	/**
	 * content 가져오기
	 * @return
	 */
	public String getContent() {return content;}
	/**
	 * 날짜 가져오기
	 * 숫자로된 날짜값을 실제 날짜로 변경
	 * @return
	 */
	public String getDatetime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(datetime);
	}

	/**
	 * 내용 업데이트
	 * @param name
	 * @param content
	 */
	public void update(String name, String content){
		this.name = name;
		this.content = content;
		this.datetime = System.currentTimeMillis();
	}
}
```
