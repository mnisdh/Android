# 잔돈계산(인터페이스 사용) / Control flow 설명

## 잔돈계산(인터페이스 사용)
	인터페이스를 사용하여 잔돈계산하는 코드 작성

- ChangeMain Class(진입점)
 	- 지불금액 : {0} / 구입금액 : {1} 입력받아 잔돈계산  
	```java
 	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);

		System.out.print("지불금액 : ");
		int num1 = scanner.nextInt();
		System.out.print("구입금액 : ");
		int num2 = scanner.nextInt();

		//반복문 사용한 코드
		Change change = new Change();
		change.calc(num1, num2);

		//반복문 사용안한 코드
		//ChangeMain cMain = new ChangeMain();
		//cMain.calc(num1, num2);
	}
	```

- Design.IChange Interface(계산메소드를 설계한 인터페이스)
```java
	package Design;
	/**
	* 인터페이스를 설계하는 방법
	* 접근제한자 + interface + 이름
	*
	* 설계된데로 구현하도록 강제화하는 방법
	*
	* 인터페이스에 맞는 메소드별로 모아서 생성할것
	*
	* @author daeho
	*
	*/
	public interface IChange {
		public void calc(int pay, int buy);
	}
```

- Design.IChangePrint Interface(출력메소드를 설계한 인터페이스)
```java
		package Design;

		public interface IChangePrint {
				public void print(String flag, int count);
		}
```

- Change Class(위의 두 인터페이스를 구현한 잔돈계산 클래스)
```java
/**
 * 인터페이스를 구현하기
 * class + 클래스명 + implements + 인터페이스명
 * @author daeho
 *
 */
public class Change implements Design.IChange, Design.IChangePrint {
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

	@Override
	public void print(String flag, int count){
		System.out.printf("%d원 : %d개%n", flag, count);
	}
}
```

## Control flow 설명
	[소스코드](https://github.com/mnisdh/Android/blob/master/java/Change/src/ControlFlow.java)
