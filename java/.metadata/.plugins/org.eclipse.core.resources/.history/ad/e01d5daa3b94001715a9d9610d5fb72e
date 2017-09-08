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




















