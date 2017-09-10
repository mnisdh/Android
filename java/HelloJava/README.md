	변수, 메소드, 연산, 주석 설명

#### 변수 다루는 법
1. int

```java
  //정수 연산은 int로 대체된다
  byte a = 10;
  byte b = 11;
  //byte c = a + b; (x)
  //int c = a + b; (o)
  byte c = (byte) (a + b);
```


2. long

```java
  //long 선언시 값마지막에 L붙여줌
  long longA = 213984342L;
```


3. double, float

```java
  //실수는 모두 double 로 대체된다
  double dVal = 2.55556;
  //float일 경우 값마지막에 f붙여줌
  float fVal = 2.55556f;
```


4. 실수 연산

```java
  //실수는 직접연산하지 않는다
  float d = 3.14f;
  float e = 4.1928f;
  //float f = d + e;  (x)
  float f = Float.sum(d, e);

  double g = 5.223;
  double h = 1.98343;
  double i = Double.sum(g, h);
```


5. 우선순위

```java
  //연산자 우선순위는 괄호로 하고 부족한부분은 검색
  int j = (30 + 1) * 2 / (50 - 3);
```


6. int to String 메소드

```java
  	/**
   * 숫자를 문자로 변환
   * @param number
   * @return
   */
  public String changeNumberToString(int number){
  	return number + "";
  }
```


7. String to int 메소드

```java
  	/**
   * 숫자인 문자열을 숫자로 변환
   * @param word
   * @return
   */
  public int changeStringToInteger(String word){
  	return Integer.parseInt(word);
  }
```


8. String to long 메소드

```java
  	/**
   * long숫자인 문자열을 long으로 변환
   * @param word
   * @return
   */
  public long changeStringToLong(String word){
  	return Long.parseLong(word);
  }
```


#### 메소드 구조

```java
  접근제한자 리턴타입 함수명(파라미터타입 파라미터) {  
  public  
  private  
  protected  
  (default)  
  }  
  파라미터 사용 : java -c HelloMain.class a b c 1 22
```


#### 연산 테스트

```java
  /**
     * 연산테스트 메소드
     */
    private static void calcTest(){
    	int a = 2 + 3;
    	int b = 2 - 3;
    	int c = 2 * 3;
    	int d = 2 / 3;//소수점이하 반올림(자동형변환)
    	int e = 5 % 3;//5를 3으로 나눈 나머지
    	int f = 10 % 3;//10을 3으로 나눈 나머지
    	double dou = 5.0 % 4.2;
    	System.out.println("2 + 3 = " + a);
    	System.out.println("2 - 3 = " + b);
    	System.out.println("2 * 3 = " + c);
    	System.out.println("2 / 3 = " + d);
    	System.out.println("5 % 3 = " + e);
    	System.out.println("10 % 3 = " + f);
    	System.out.println("5.0 % 4.2 = " + dou);
    }
```


#### 주석 종류

```java
1.
  // 주석으로 처리할 내용

2.   
  /* 주석으로
      처리할
      내용   */

3.
  /**
  * 자바독에서 사용가능한 주석
    */
```
