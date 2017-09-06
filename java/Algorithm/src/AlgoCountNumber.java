/**
 * 입력값을 정수 m, 한자리수 정수 n으로 받았을때
 * 1부터 정수 m까지 중에 정수 n이 몇개가 있는지 카운트 하세요
 * 
 * ex) 입력값 m = 10000, n = 8
 *     1부터 10000사이에 8이라는 숫자가 몇개가 있는지 카운트 하는데
 *     8888의 경우 4로 카운트됩니다.
 * @author daeho
 *
 */
public class AlgoCountNumber {
	public int count(int m, int n){
		int result = 0;
		String findNum = n + "";
		
		for(int i = 1; i <= m; i++){
			String str = i + "";
			
			// findNum이 포함된 경우 카운팅 함수로 이동
			if(str.contains(findNum)) result += getCount(str.split(""), findNum);
		}
		
		return result;
	}
	
	/**
	 * targets 문자열중 find 문자열과 일치하는 문자열의 갯수 카운팅
	 * @param targets
	 * @param find
	 * @return
	 */
	private int getCount(String[] targets, String find){
		int count = 0;
		
		for(String s : targets) if(s.equals(find)) count++;
		
		return count;
	}
}
