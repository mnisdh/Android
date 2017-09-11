package com.daehoshin.java.generic;

public class GenericMain {

	public static void main(String[] args) {
		DHList<test> list = new DHList<test>();
		
		System.out.println(list.size());	
		
		list.add(new test("123"));
		list.add(new test("abc"));
		list.add(new test("zzz"));
		list.add(new test("kkkkk"));
		
		for(int i = 0; i < list.size(); i++) System.out.println(list.get(i).name);
		
		System.out.println("--------------------------");
		
		list.add(1, new test("aaa"));
		
		for(int i = 0; i < list.size(); i++) System.out.println(list.get(i).name);
	}

}


class test{
	public String name = "test";
	public test(String aa){
		name = aa;
	}
	
}























