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
