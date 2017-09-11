### Algorithm

##### 숫자계산
- 1 ~ max까지 더하는 메소드

```
	1 2 3 4 5 6 7 8
	8 7 6 5 4 3 2 1
	| | | | | | | |
	9 9 9 9 9 9 9 9

	(1 + 8) * 8 / 2 = 36
```
```java
/**
 * 1 ~ max 까지 더하는 함수
 * @param max
 * @return
 */
public long sum(long max){
	long sum = (max + 1) * max / 2;
	//for(int i = 1; i <= max; i++) sum += i;

	return sum;
}
```


- 1 ~ max까지 홀수만 더하는 메소드

```
	1 3 5     = 9     3 * 3 = 9
	1 3 5 7   = 16    4 * 4 = 16
	1 3 5 7 9 = 25    5 * 5 = 25

	홀수갯수의 제곱
```
```java
/**
 * 1 ~ max 까지 홀수만 더하는 함수(홀수갯수의 제곱)
 * @param max
 * @return
 */
public long sumOdd(long max){
	long count = 0;
	if(max % 2 == 1) max++;

	count = max / 2;

	return count * count;
}
```


- 1 ~ max까지 짝수만 더하는 메소드

```
	2 4     = 9     2 * 2 + 2 = 6
	2 4 6   = 16    3 * 3 + 3 = 12
	2 4 6 8 = 25    4 * 4 + 4 = 20

	짝수갯수의 제곱 + 짝수갯수
```
```java
/**
 * 1 ~ max 까지 짝수만 더하는 함수(짝수갯수의 제곱 + 짝수갯수)
 * @param max
 * @return
 */
public long sumEven(long max){
	long count = 0;
	if(max % 2 == 1) max--;

	count = max / 2;

	return count * count + count;
}
```

##### Math 관련

- abs - 절대값 구하기

```java
// 절대값 구하기
int a = Math.abs(-123);
```


- round - 반올림 구하기

```java
// 반올림
long b = Math.round(123.5);
```


- ceil - 올림 구하기

```java
// 올림
double c = Math.ceil(123.4);
```


- floor - 내림 구하기

```java
// 내림
double d = Math.floor(123.5);
```


- random - 랜덤값 구하기

```java
Math.random(); // 0보다 크가나 같고 1보다 작은 실수를 리턴
```


##### 일반적인 Random값 사용 관련

```java
		Random random = new Random();
		// 1부터 100사이의 랜덤한 숫자 가져오기
		random.nextInt(100); // 0 ~ 99사이의 정수가 리턴
		int r = random.nextInt(100) + 1; // 1~ 100사이의 정수가 리턴
```


##### 숫자 카운팅 연습

```java
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
```


##### 로또번호 생성기

랜덤값을 사용한 로또번호 생성기 연습

```java
/**
 * 로또번호 생성기
 * 랜덤으로 번호 받아올때 중복된 값이있을 수 있으므로 중복체크 필요함
 * @return
 */
public int[] getLottoNumber(){
	int[] result = new int[6];
	Random random = new Random();

	for(int i = 0; i < 6; i++) {
		int num = 0;
		do{
			num = random.nextInt(45) + 1;
		}while(existNum(result, num));

		result[i] = num;
	}

	return result;
}

/**
 * nums에 num이 포함된 여부확인
 * @param nums
 * @param num
 * @return
 */
 private boolean existNum(int[] nums, int num){
 		for(int item : nums) if(item == num) return true;

 		return false;
 	}
```
