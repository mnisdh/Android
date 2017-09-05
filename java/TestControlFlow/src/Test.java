
public class Test {
	// 입력 1 : 타입이 String인 마크가 되는 문자열
	// 입력 2 : 출력할 줄 수
	
	/**
	 * count만큼 mark를 print로 출력
	 * @param mark
	 * @param count
	 */
	private void printMark(String mark, int count){
		for(int i = 0; i < count; i++){
			System.out.print(mark);
		}
	}
	/**
	 * count만큼 돌면서 index%2==1 인경우만 mark출력 아닌경우는 " "출력
	 * @param mark
	 * @param count
	 */
	private void printMarkDash(String mark, int count){
		for(int i = 0; i < count; i++){
			if((i + 1) % 2 == 1) System.out.print(mark);
			else System.out.print(" ");
		}
	}
	/**
	 * count만큼 돌면서 처음과 끝에만 mark출력 아닌경우는 " "출력
	 * @param mark
	 * @param count
	 */
	private void printMarkStartEnd(String mark, int count){
		for(int i = 0; i < count; i++){
			if(i == 0 || i == count - 1) System.out.print(mark);
			else System.out.print(" ");
		}
	}
	
	/**
	 * 난이도 1
	 * @param mark
	 * @param lines
	 */
	public void run1(String mark, int lines){
		for(int i = 0; i < lines; i++){
			printMark(mark, i + 1);
			
			System.out.println("");
		}
	}
	
	/**
	 * 난이도 2
	 * @param mark
	 * @param lines
	 */
	public void run2(String mark, int lines){
		for(int i = 0; i < lines; i++){
			printMark(" ", lines - (i + 1));
			printMark(mark, i + 1);
			System.out.println("");
		}
	}
	
	/**
	 * 난이도 3
	 * @param mark
	 * @param lines
	 */
	public void run3(String mark, int lines){
		int nextMarkCount = 0;
		
		for(int i = 0; i < lines; i++){
			printMark(" ", lines - (i + 1));
			printMark(mark, i + 1);
			printMark(mark, nextMarkCount);
			System.out.println("");
			
			nextMarkCount++;
		}
	}
	
	/**
	 * 난이도 3.5
	 * @param mark
	 * @param lines
	 */
	public void run4(String mark, int lines){
		boolean useNextMark = false;
		for(int i = 0; i < lines; i++){
			printMark(" ", lines - (i + 1));
			printMarkStartEnd(mark, i * 2 + 1);
			System.out.println("");
			
			useNextMark = true;
		}
	}
	
	/**
	 * 난이도 3.6
	 * @param mark
	 * @param lines
	 */
	public void run5(String mark, int lines){
		int nextMarkCount = 0;
		boolean useNextMark = false;
		for(int i = 0; i < lines; i++){
			if(i == lines - 1){
				printMark(mark, i + 1);
				printMark(mark, nextMarkCount);
			}else{
				printMark(" ", lines - (i + 1));
				printMark(mark, 1);
				printMark(" ", i * 2 - 1);
				if(useNextMark) printMark(mark, 1);
			}
			System.out.println("");
			
			useNextMark = true;
			nextMarkCount++;
		}
	}
	
	/**
	 * 난이도 3.8
	 * @param mark
	 * @param lines
	 */
	public void run6(String mark, int lines){
		run3(mark, lines);
		run6sub(mark, lines - 1);
	}
	private void run6sub(String mark, int lines){
		int spaceCount = 1;
		
		for(int i = lines - 1; i >= 0; i--){
			printMark(" ", spaceCount);
			printMark(mark, i + 1);
			printMark(mark, i);
			System.out.println("");
			
			spaceCount++;
		}
	}
	
	/**
	 * 난이도 4
	 * @param mark
	 * @param lines
	 */
	public void run7(String mark, int lines){
		int nextMarkCount = 0;
		for(int i = 0; i < lines; i++){
			printMark(" ", lines - (i + 1));
			printMarkDash(mark, i + 1 + nextMarkCount);
			System.out.println("");
			
			nextMarkCount++;
		}
		run6sub(mark, lines - 1);
	}
	
	/**
	 * 난이도 5
	 * @param mark
	 * @param lines
	 */
	public void run8(String mark, int lines){
		run4(mark, lines);
		run8sub(mark, lines - 1);
	}
	public void run8sub(String mark, int lines){
		int spaceCount = 1;
		
		for(int i = lines - 1; i >= 0; i--){
			printMark(" ", spaceCount);
			printMarkStartEnd(mark, i * 2 + 1);
			System.out.println("");
			
			spaceCount++;
		}
	}
	public void run8sub(String mark, int startIndex, int endIndex){
		int spaceCount = 0;
		
		for(int i = endIndex; i >= startIndex; i--){
			printMark(" ", spaceCount);
			printMarkStartEnd(mark, i * 2 + 1);
			System.out.println("");
			
			spaceCount++;
		}
	}
	
	
	/**
	 * 난이도 5.5
	 * @param mark
	 * @param lines
	 */
	public void run9(String mark, int lines){
		run8sub(mark, 1, lines - 1);
		run4(mark, lines);
	}
	
	/**
	 * 난이도 5.7
	 * @param mark
	 * @param lines
	 */
	public void run10(String mark, int lines){
		int maxCount = lines * 2 - 1;
		
		for(int i = 0; i < maxCount; i++){
			if(i == 0 || i == maxCount - 1) printMark(mark, maxCount);
			else printMarkStartEnd(mark, maxCount);
			
			System.out.println("");
		}
	}
	
	
	/**
	 * 난이도 6 (입력값 = 7)
	 * @param mark
	 * @param lines
	 */
	public void run11(String mark, int lines){
		int firstSpaceCount = 0;
		for(int i = lines; i > 0; i--){
			printMark(" ", firstSpaceCount);
			run11sub(mark, i);
			System.out.println("");
			
			firstSpaceCount += i - 1;
		}
	}
	private void run11sub(String mark, int count){
		for(int i = count; i > 0; i--){
			printMark(mark, 1);
			printMark(" ", count - 1);
		}
	}
	
	
	/**
	 * 난이도 7 (입력값 = 6)
	 * @param mark
	 * @param lines
	 */
	public void run12(String mark, int lines){
		for(int i = 0; i < lines; i++){
			printMark(" ", getFirstSpaceCount(lines, i));
			printMark(mark, 1);
			printMark(" ", getCenterSpaceCount(lines, i));
			printMark(mark, 1);
			System.out.println("");	
		}
	}
	private int getFirstSpaceCount(int lines, int index){
		int firstSpaceCount = lines;
		
		int count = 1;
		for(int i = lines - 1; i >= 0; i--){
			if(i == index) break;
			
			firstSpaceCount += count;
			count++;
		}
		
		return firstSpaceCount;
	}
	private int getCenterSpaceCount(int lines, int index){
		int centerSpaceCount = 0;
		
		for(int i = 0; i < lines; i++){
			centerSpaceCount += (lines - i) * 2;
			
			if(index == i) break;
		}
		
		return centerSpaceCount;
	}
	
}
