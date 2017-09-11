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