### MVC 패턴

하나의 어플리케이션을 크게 Model, View, Controller 의 세영역으로 구분하고 각 영역간의 코드 결합도를 최소화 시키는 개발 패턴중 하나이다

- Model : 데이터와 상태를 유지하며 데이터 처리 로직을 포함(데이터를 의미)
- View : 사용자가 보는 것을 의미(입/출력)
- Controller : 사용자가 보기 위해 데이터를 가공, 혹은 조작 하는 것을 의미(계산메소드등)


### File Input / Output

##### Input

```java
public ArrayList<Memo> getList(){
	list.clear();

	// 1. 읽는 스트림을 연다
	try(FileInputStream fis = new FileInputStream(database)){
		// 2. 실제 파일 인코딩을 바꿔주는 래퍼 클래스 사용
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");

		// 3. 버퍼처리
		BufferedReader br = new BufferedReader(isr);

		// readLine이 null이 아닐때까지 실행
		String row;
		while((row = br.readLine()) != null){
			String[] tempRow = row.split(COLUMN_SEP);
			if(tempRow.length == 4){
				Memo memo = new Memo(tempRow[1], tempRow[2]);
				memo.setNo(Integer.parseInt(tempRow[0]));
				memo.setDatetime(Long.parseLong(tempRow[3]));

				list.add(memo);
			}
		}
		br.close();
		isr.close();
	}catch(Exception ex){
		ex.printStackTrace();
	}

	return list;
}
```


##### Output

```java
public void create(Memo memo){
	int newCount = getCount() + 1;
	memo.setNo(newCount);

	// memo 객체의 내용을 파일에 쓴다
	FileOutputStream fos = null;

	//try with문법 ( ) 안의 객체는 문법종료 후 자동으로 close해줌
	//try(FileOutputStream fos = new FileOutputStream(database, true))
	try{
		// 1. 쓰는 스트림을 연다
		fos = new FileOutputStream(database, true);

		// 2. 스트림 중간처리를 함(인코딩 변경 등....)
		OutputStreamWriter osw = new OutputStreamWriter(fos);

		// 3. 버퍼처리
		BufferedWriter bw = new BufferedWriter(osw);

		// 저장할 내용을 구분자로 분리하여 한줄의 문자열로 바꾼다
		String str = memo.getNo() + COLUMN_SEP;
				 str += memo.getName() + COLUMN_SEP;
				 str += memo.getContent() + COLUMN_SEP;
				 str += memo.getDatetimeLong() + "\n";

		bw.append(str);
		bw.close();
		osw.close();

		updateCount(newCount);
	} catch(Exception ex) {
		ex.printStackTrace();
	} finally {
		// 스트림이 생성되기전에 오류가 발생할 수 있으므로 null 체크 필요
		if(fos != null){
			try {
				fos.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
```

### mysql 연동

##### Connector 설치

- osx의 경우 [Connector/j](https://dev.mysql.com/downloads/connector/j/5.1.html)를 다운로드한다
- 이클립스 프로젝트 속성 - Java build path - Libraries - Add External jre로 다운받은 파일 추가하면 완료


##### 사용법

- 드라이버 동적 로드

```java
public ModelWithDB(){
	// 특정 컴퓨터를 찾기위한 주소 체계
	// 아이피 = 213.12.142.132
	// url = naver.com
	//
	// 특정 프로그램에 할당되는 세부 번지
	// port = 1 ~ 6만번대
	//        2000번대 밑은 이미 표준으로 사용되고 있다.
	//
	// 소켓 : 아이피 + 포트

	// 표준 프로토콜
	// http://아이피(주소) : 포트(80)

	// 특정 프로그램에 액세스 하기위한 주소체계
	// 프로토콜이름 :// 아이피(주소) : 포트

	// ("데이터베이스주소","아이디","비밀번호")
	try{
		Class.forName("com.mysql.jdbc.Driver"); //드라이버를 동적으로 로드
	}catch(Exception ex){
		ex.printStackTrace();
	}
}
```


- Connection 맺기

```java
private final String URL = "jdbc:mysql://localhost:3306/memo";
private final String USER = "root";
private final String PASS = "root";

private Connection getConnection(){
	try{
		return DriverManager.getConnection(URL, USER, PASS);
	}catch(Exception ex){
		System.out.println("접속실패");
		ex.printStackTrace();
	}

	return null;
}
```


- Statement를 사용한 쿼리실행

```java
public ArrayList<Memo> getList(){

	ArrayList<Memo> list = new ArrayList<Memo>();

	// 1. 데이터베이스 연결
	Connection conn = getConnection();
	if(conn ==null) return list;

	// 2. 쿼리를 실행
	try{
		// 2.1 쿼리 생성
		String sql = " select * from memo ";
		// 2.2 쿼리를 실행 가능한 상태로 만들어준다
		Statement stmt = conn.createStatement();
		// 2.3 select한 결과값을 돌려받기 위해 쿼리를 실행
		ResultSet rs = stmt.executeQuery(sql);
		// 결과셋을 반복하면서 하나씩 꺼낼 수 있다
		while(rs.next()){
			Memo memo = new Memo(rs.getString("name"), rs.getString("content"));
			memo.setNo(rs.getInt("no"));
			memo.setDatetime(rs.getLong("datetime"));

			list.add(memo);
		}

		// 3. 데이터베이스 연결해제
		conn.close();
	}catch(Exception ex){
		ex.printStackTrace();
	}

	return list;
}
```


- PreparedStatement를 사용한 쿼리실행(Statement 보다 속도가 빠르다)

```java
public void create(Memo memo){
	// 1. 데이터베이스 연결
	Connection conn = getConnection();
	if(conn ==null) return;

	// 2. 쿼리를 실행
	try{
		// 2.1 쿼리 생성
		String sql = " insert into memo(name,content,datetime) values(?,?,?) ";

		// 2.2 쿼리를 실행 가능한 상태로 만들어준다
		PreparedStatement pstmt = conn.prepareStatement(sql);
		// 2.3 물음표에 값을 세팅
		pstmt.setString(1, memo.getName());
		pstmt.setString(2, memo.getContent());
		pstmt.setTimestamp(3, new Timestamp( System.currentTimeMillis()));
		// 2.4 쿼리를 실행
		pstmt.executeUpdate();

		// 3. 데이터베이스 연결해제
		conn.close();
	}catch(Exception ex){
		ex.printStackTrace();
	}
}
```


### MVC패턴, mysql을 사용한 메모저장 시스템

##### MemoMain.java(메인 : 각클래스들을 연결해주는 역할)

```java
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
```


##### ModelWithDB.java(Model : mysql연동하여 데이터를 처리하는 역할)

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * 데이터를 저장하는 저장소를 관리하는 클래스
 *
 * @author daeho
 *
 */
public class ModelWithDB {
	private final String URL = "jdbc:mysql://localhost:3306/memo";
	private final String USER = "root";
	private final String PASS = "root";

	/**
	 * new 하는 순간 실행됨 (생성자)
	 */
	public ModelWithDB(){
		// 특정 컴퓨터를 찾기위한 주소 체계
		// 아이피 = 213.12.142.132
		// url = naver.com
		//
		// 특정 프로그램에 할당되는 세부 번지
		// port = 1 ~ 6만번대
		//        2000번대 밑은 이미 표준으로 사용되고 있다.
		//
		// 소켓 : 아이피 + 포트

		// 표준 프로토콜
		// http://아이피(주소) : 포트(80)

		// 특정 프로그램에 액세스 하기위한 주소체계
		// 프로토콜이름 :// 아이피(주소) : 포트

		// ("데이터베이스주소","아이디","비밀번호")
		try{
			Class.forName("com.mysql.jdbc.Driver"); //드라이버를 동적으로 로드
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private Connection getConnection(){
		try{
			return DriverManager.getConnection(URL, USER, PASS);
		}catch(Exception ex){
			System.out.println("접속실패");
			ex.printStackTrace();
		}

		return null;
	}

	public ArrayList<Memo> getList(){

		ArrayList<Memo> list = new ArrayList<Memo>();

		// 1. 데이터베이스 연결
		Connection conn = getConnection();
		if(conn ==null) return list;

		// 2. 쿼리를 실행
		try{
			// 2.1 쿼리 생성
			String sql = " select * from memo ";
			// 2.2 쿼리를 실행 가능한 상태로 만들어준다
			Statement stmt = conn.createStatement();
			// 2.3 select한 결과값을 돌려받기 위해 쿼리를 실행
			ResultSet rs = stmt.executeQuery(sql);
			// 결과셋을 반복하면서 하나씩 꺼낼 수 있다
			while(rs.next()){
				Memo memo = new Memo(rs.getString("name"), rs.getString("content"));
				memo.setNo(rs.getInt("no"));
				memo.setDatetime(rs.getLong("datetime"));

				list.add(memo);
			}

			// 3. 데이터베이스 연결해제
			conn.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}

		return list;
	}

	/**
	 * list에 memo 추가
	 * @param memo
	 */
	public void create(Memo memo){
		// 1. 데이터베이스 연결
		Connection conn = getConnection();
		if(conn ==null) return;

		// 2. 쿼리를 실행
		try{
			// 2.1 쿼리 생성
			String sql = " insert into memo(name,content,datetime) values(?,?,?) ";

			// 2.2 쿼리를 실행 가능한 상태로 만들어준다
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 2.3 물음표에 값을 세팅
			pstmt.setString(1, memo.getName());
			pstmt.setString(2, memo.getContent());
			pstmt.setTimestamp(3, new Timestamp( System.currentTimeMillis()));
			// 2.4 쿼리를 실행
			pstmt.executeUpdate();

			// 3. 데이터베이스 연결해제
			conn.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * list의 memo 삭제
	 * @param memo
	 */
	public void delete(Memo memo){
		// 1. 데이터베이스 연결
		Connection conn = getConnection();
		if(conn ==null) return;

		// 2. 쿼리를 실행
		try{
			// 2.1 쿼리 생성
			String sql = " delete from memo where no = " + memo.getNo();
			// 2.2 쿼리를 실행 가능한 상태로 만들어준다
			Statement stmt = conn.createStatement();
			// 2.3 delete 쿼리를 실행
			stmt.execute(sql);

		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				// 3. 데이터베이스 연결해제
				conn.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

	/**
	 * list의 memo 수정
	 * @param memo
	 */
	public void update(Memo memo){
		// 1. 데이터베이스 연결
		Connection conn = getConnection();
		if(conn ==null) return;

		// 2. 쿼리를 실행
		try{
			// 2.1 쿼리 생성
			String sql = " update memo set name = ?, context = ?, datetime =? where no = ?";

			// 2.2 쿼리를 실행 가능한 상태로 만들어준다
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 2.3 물음표에 값을 세팅
			pstmt.setString(1, memo.getName());
			pstmt.setString(2, memo.getContent());
			pstmt.setTimestamp(3, new Timestamp( System.currentTimeMillis()));
			pstmt.setInt(4, memo.getNo());
			// 2.4 쿼리를 실행
			pstmt.executeUpdate();

			// 3. 데이터베이스 연결해제
			conn.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * list에서 no같은 memo 가져오기
	 * @param no
	 * @return
	 */
	public Memo getMemo(int no){
		// 1. 데이터베이스 연결
		Connection conn = getConnection();
		if(conn ==null) return null;

		// 2. 쿼리를 실행
		try{
			// 2.1 쿼리 생성
			String sql = " select * from memo where no =" + no;
			// 2.2 쿼리를 실행 가능한 상태로 만들어준다
			Statement stmt = conn.createStatement();
			// 2.3 select한 결과값을 돌려받기 위해 쿼리를 실행
			ResultSet rs = stmt.executeQuery(sql);
			// 결과셋을 반복하면서 no
			while(rs.next()){
				Memo memo = new Memo(rs.getString("name"), rs.getString("content"));
				memo.setNo(rs.getInt("no"));
				memo.setDatetime(rs.getTimestamp("datetime").getTime());

				return memo;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				// 3. 데이터베이스 연결해제
				conn.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return null;
	}
}
```


##### View.java(View : 화면의 입출력을 관리하는 역할)

```java
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
```


##### Control.java(Controller : Model과 View를 연결해주는 역할)

```java
import java.util.Scanner;

public class Control {
	public void process(ModelWithDB model, View view) {
		// view의 scanner 불러다가 사용
		Scanner scanner = view.getScanner();

		String command = "";
		while(!command.equals("exit")){

			view.println("--------------- 명령어를 입력하세요 ---------------");
			view.println("전체목록(l), 추가(c), 읽기(r), 수정(u), 삭제(d)");
			view.println("exit - 종료");
			view.println("----------------------------------------------");

			command = scanner.nextLine(); // 엔터키가 입력될때까지 대기

			// 명령어 분기처리
			switch(command){
			case "l":
				view.showList(model.getList());
				break;

			case "c":
				Memo cMemo = view.create();
				model.create(cMemo);
				break;

			case "r":
				int rNo = view.getNo();
				Memo rMemo = model.getMemo(rNo);
				if(rMemo != null) view.read(rMemo);
				else view.println("없는 번호입니다.");
				break;

			case "u":
				int uNo = view.getNo();
				Memo uMemo = model.getMemo(uNo);
				if(uMemo != null) {
					view.update(uMemo);
					model.update(uMemo);
				}
				else view.println("없는 번호입니다.");
				break;

			case "d":
				int dNo = view.getNo();
				Memo dMemo = model.getMemo(dNo);
				if(dMemo != null) {
					model.delete(dMemo);
					view.delete();
				}
				else view.println("없는 번호입니다.");
				break;
			}
		}

		view.println("시스템이 종료되었습니다.");
	}
}
```
