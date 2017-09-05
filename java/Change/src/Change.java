

/**
 * 인터페이스를 구현하기
 * class + 클래스명 + implements + 인터페이스명
 * @author daeho
 *
 */
public class Change implements Design.IChange, Design.IChangeMove {
	int[] changeArray = {5000, 1000, 500, 100, 50, 10};
	
	/**
	 * 거스름돈 계산
	 * ---------반복---------------------
	 * 잔돈계산
	 * 잔돈을 현재 거스름돈으로 나눈후 나머지 저장
	 * ---------------------------------
	 * @param pay
	 * @param buy
	 */
	@Override
	public void calc(int pay, int buy){
		int result = pay - buy;
		
		//System.out.printf("총거스름돈 : %,d원%n", result);
		print("총거스름돈", result);
		
		for(int change : changeArray){
			//System.out.printf("%d원 : %d개%n", change, result / change);
			print(change + "", result / change);
			result %= change;
		}	
	}
	
	private void print(String flag, int count){
		System.out.printf("%d원 : %d개%n", flag, count);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		// 구현단의 코드를 작성
		
	}
}
