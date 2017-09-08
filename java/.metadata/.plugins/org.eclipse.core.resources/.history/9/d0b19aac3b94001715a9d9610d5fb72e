import java.util.ArrayList;

/**
 * 데이터를 저장하는 저장소를 관리하는 클래스
 * 
 * @author daeho
 *
 */
public class Model {
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
