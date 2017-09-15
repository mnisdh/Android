# Annotation(Metadata 라고도 불림)
- 클래스, 메소드, 변수등 모든요소에 선언 가능
- 컴파일러에게 정보를 알려줄때 사용하거나
- 컴파일할때 설치시의 작업을 지정하거나
- 실행할 때 별도의 처리가 필요할때 사용


## 일반적으로 사용하는 Annotation

- @Override : 해당 메소드가 부모클래스의 메소드를 오버라이드 했다는것을 명시적으로 선언

- @Deprecated : 더이상 사용되지 않는 클래스나 메소드를 표시

- @SuppressWarnings : 프로그램에 문제는 없으나 컴파일러가 경고뿌리는 것을 없앨때 사용(되도록 안쓰는게 좋은듯)


## Meta Annotation

- @Target : 어떤것에 적용할것인지

```
CONSTRUCTOR 			: 생성자 선언시
FIELD 						: enum 상수를 포함한 필드값 선언시
LOCAL_VARIABLE 		: 지역 변수 선언시
METHOD 						: 메소드 선언시
PACKAGE 					: 패키지 선언시
PARAMETER 				: 매개변수 선언시
TYPE 							: 클래스, 인터페이스, enum 등 선언시
```


- @Retention : 얼마나 오래 정보가 유지되는지

```
SOURCE  	: 컴파일시 사라짐
CLASS 		: 컴파일러에 의해 참조가능하나 가상머신에서는 사라짐
RUNTIME 	: 가상머신에 의해서 참조가능
```


- @Inherited : 모든 자식 클래스가 부모 클래스의 어노테이션을 사용할 수 있다는 선언


- @interface : customAnnotation 생성시 사용


## 생성예제

```java
package com.daehoshin.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class AnnoMain {

	public static void main(String[] args) {
		UseAnnotation use = new UseAnnotation();

		String key = use.getClass().getAnnotation(CustomAnnotation.class).key();
		if(key.equals("Student")){
			//런타임시에 해줄 행동을 정의
		}

	}

}


/**
 * Target = 적용할 대상 : 생성자, 멤버변수, 타입(클래스), 파라미터, 메소드
 * Retention = 에너테이션 정보의 유지단계
 *             소스코드, 클래스, 런타임...
 * Documented = javadoc에 문서화 되어져야하는 엘리먼트
 * Inherited = 상속되는 애너테이션
 *
 * 주로 target, retention을 사용함
 *
 * @author daeho
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface CustomAnnotation{
	public String value() default "값";
	public String key();
}

//@CustomAnnotation(key = "추가된 키값")
//@CustomAnnotation(key = "Student")
class UseAnnotation{
	@CustomAnnotation(key="asdf")
	public void aaa(){

	}
}
```
