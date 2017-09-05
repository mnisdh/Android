# OOP_Solid
	OOP 설계에 대해서 알아봅니다
 	- OOP란 Object Oriented Programming 의 약자로 객체지향 프로그래밍이라함
 	- 객체지향 : 속성과 기능이 묶인 하나의 객체(Class) 단위로 프로그램을 설계 구현하는것
[BasicSyntax 기본적인 문법.pdf]( https://github.com/mnisdh/Android/tree/master/java/Solid/pdf/002_01_BasicSyntax기본적인문법.pdf)  
[Class와 Object에 대한 이해.pdf](https://github.com/mnisdh/Android/tree/master/java/Solid/pdf/004_Class와Object에대한이해.pdf)

## SRP - Single Responsibility Principle
	단일 책임의 원칙
	1. 하나의 클래스는 하나의 역할만 맡는다
	2. 코드 변경의 영향이 미치는 범위가 최소화 된다

## OCP - Open Closed Principle
	개방 / 폐쇄의 원칙
	1. 확장에 열려있고 수정에 닫혀 있다
	2. 클래스간 통신을 위한 인터페이스가 확정되면 수정하지 않는다
 	- 다른 기능이 필요할 경우 확장(extends)를 통해 기능을 추가한다
	- 인터페이스가 변경되면 해당 인터페이스를 사용한 모든 클래스가 변경되야 되므로 지양해야함

## LSP - Liskov Substitution Principle
	리스코프 교체
	1. 파생 클래스는 상위 클래스로 대체 가능해야 한다.
	2. 상위 클래스의 기능을 파생 클래스가 포함해야 한다
	- 상위클래스 a = new 하위클래스()

## ISP - Interface Segregation Principle
	인터페이스 격리 원칙
	1. 특화된 여러 개의 인터페이스가 범용 인터페이스 한 개 보다 났다
	2. 클라이언트는 자신이 쓰지 않는 인터페이스에 의존하지 않는다

## DIP - Dependency Inversion Principle
	의존 관계 역전 원칙
	1. 추상화 된것은 구체적인 것에 의존하면 안된다(상위 모듈이 하위 모듈에 의존하면 안된다)
	2. 자주변경되는 구체(Concrete) 클래스에 의존하면 안된다
