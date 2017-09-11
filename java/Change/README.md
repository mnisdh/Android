	Interface를 사용하여 잔돈계산하는 시스템 작성 / 조건문 반복문 설명

### 잔돈계산 시스템(Interface 사용)
##### ChangeMain class(main)

```java
// 지불금액과 구입금액을 입력받아 잔돈 계산
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


##### Design.IChange interface(계산 메소드를 설계한 인터페이스)

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


##### Design.IChangePrint interface(출력 메소드를 설계한 인터페이스)

```java
package Design;

public interface IChangePrint {
		public void print(String flag, int count);
}
```


##### Change class(IChange, IChangePrint를 구현한 잔돈계산 class)

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


### 조건문

##### if문

```java
/**
 * 조건문 if
 */
public void checkIf(){
	int a = 10;
	int b = 5;

	if(a > b){
		//a가 b보다 크면 실행되는 영역
		System.out.println("a가 b보다 큽니다.");
	}else if(a == b){
		//a와 b가 같으면 실행되는 영역
	}else{
		//그외 조건일때 실행되는 영역
	}

}
```


##### switch문

```java
/**
 * 조건문 switch
 * 한개의 변수를 대상으로 값이 같은지 체크할때 사용
 */
public void checkSwitch(){
	int a = 10;

	switch(a){
	case 5:
		System.out.println("a의 값이 5입니다.");
			break; //break가 없을시 아래코드까지 실행되므로 예외사항아닐시엔 항상 넣을것
	case 10:
		System.out.println("a의 값이 10입니다.");
		break;
	}
}
```


### 반복문

##### for문

```java
/**
 * 반복문 for
 * 배열의 인덱스는 0부터시작함
 */
public void checkFor(){
	int[] array = {1, 2, 3, 4, 5, 6, 7};
	//array[0] == 1
	//array[1] == 2

	//일반적인 for문
	//for(시작값; 조건; 증감값)
	for(int i = 0; i < array.length; i++){
		System.out.println(array[i]);
	}
	/* result
	 * 1
	 * 2
	 * 3
	 * 4
	 * 5
	 * 6
	 * 7
	 */

	//for(꺼낼타입 변수명 : 꺼낼배열)  향상된 for
	for(int item : array){
		System.out.println(item);
	}
	/* result
	 * 1
	 * 2
	 * 3
	 * 4
	 * 5
	 * 6
	 * 7
	 */
}
```


##### while문

```java
/**
 * 반복문 while
 * 반복이 가능한 if문
 */
public void checkWhile(){
	int[] array = {1, 2, 3, 4, 5, 6, 7};

	int count = 0; // 시작값

	// while(조건식)  false가 될때까지 실행
	while(count < array.length){ // 종료값
		System.out.println(array[count]);
		count++; // 증감값
	}
}
```


##### do while문

```java
/**
 * 반복문 do while
 * While은 조건식 체크후 동작하지만 Do While은 do블럭 실행후 조건식을 체크하므로
 * do블럭은 반드시 1번은 실행되는 방식
 */
public void checkDoWhile(){
	int[] array = {1, 2, 3, 4, 5, 6, 7};

	int count = 0;

	do{
		System.out.println(array[count]);
		count++; // 증감값
	}
	while(count < array.length);
}
```
