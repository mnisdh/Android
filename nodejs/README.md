# javascript 기초문법

## 변수
변수타입 지정없이 var 로 선언하면 컴파일러가 자동으로 결정해줌

```javascript
//1. 변수타입은 var 로 항상설정
//2. 문장의 끝은 항상 ;
var name;

//3. = 을 사용해서 변수에 값을 입력하며 문자의 경우 홑따옴표, 쌍따옴표 다됨
name = '홍길동';
name = "홍길동";

//4. 문자나 숫자 및 타입에 관계없이 변수는 var 로 선언한다
var num1;
num1 = 21;

//5. 변수의 선언과 동시에 값을 입력할 수 있다
var num2 = 3;

//6. 두 개의 변수를 더해서 다른 변수에 입력할 수 있다
var sum = num1 + num2;

//7. 숫자와 문자를 더할 경우 결과값은 문자가 된다. 아래 연산결과로 sum2 에는 "홍길동21"이 sum3에는 "이순신3"이 입력된다
var sum2 = name + num1;
var sum3 = '이순신' + 3;
```

## 주석
```
// 또는 /* */
```

## 함수
함수생성 : function 함수명(파라미터){ 실행코드 }
함수호출 : 함수명(파라미터값);
실행코드에 return이 없는경우 void 함수로 인지

```javascript
// 1. 세개의 파라미터를 더한 후 결과값을 리턴하는 함수를 선언
function sum(param1, param2, param3){
    return param1 + param2 + param3;
}

// 2. 함수 실행 후 결과값을 result 에 대입
var result = sum(1,2,3);

// 3. result 에 담긴 결과값을 출력
console.log('result='+result);

// 4. 결과값이 없는 함수의 선언
function print(param1){
    console.log('param1='+param1);
}

// 5. 함수호출 : return 이 없는 함수는 로직을 자체적으로 처리하기 때문에 결과값 대입 불필요
print('출력내용');
```

## 조건문
### if
```javascript
var a = 10;

if (a > 11) {
    console.log('a가 11보다 큽니다');
} else if(a == 11) {
    console.log('a가 11과 같습니다');
} else {
    console.log('a가 11보다 작습니다');
}
```

### switch
```javascript
var i = 10;

switch (i) {
  case 1:
    console.log('i는 1입니다.');
    break;
  case 5:
    console.log('i는 5입니다.');
    break;
  case 10:
    console.log('i는 10입니다.');
    break;
  default:
    console.log('i와 맞는값이 없습니다.');
    break;
}
```

### 삼항 연산자
```javascript
var input = prompt('숫자 입력', '');
var number = Number(input);

(number > 0) ? alert('자연수') : alert('자연수아님');
```

### 짧은 조건문
```javascript
true || alert('실행될까요?A');
false || alert('실행될까요?B');
// A는 실행되지 않고 B는 실행된다.

true && alert('실행될까요?C');
false && alert('실행될까요?D');
// C는 실행되고 D는 실행되지 않는다.
```

## 반복문
### for
```javascript
for (var i = 0; i < 10; i++){
  console.log("i = " + i);
}
```

### for in
```javascript
var array = ['포도', '사과', '바나나', '망고'];

for(var i in array) {
    alert(array[i]);
}
```

### while
```javascript
var i = 0;
while(i < 10) {
    console.log("i = " + i);
    i = i + 1;
}
```

### do while
```javascript
var value = 0;
do {
    alert(value + '번째 반복문');
    value++;
} while(value < 5);
```

## Class
javascript는 프로토타입기반의 함수형 언어이므로 class는 없으며
함수형 언어들의 특징은 함수자체를 하나의 객체로 취급하기때문에
단일함수나 파일 자체를 하나의 class처럼 사용할수 있다
```javascript
//test_class.js
//class의 선언 - 낙타표기법으로 첫번째 글자를 대문자로 함수를 하나 선언한다.
function Clazz(msg){
    // 변수를 객체의 멤버로 사용하기 위해 this 예약어를 사용해서 정의한다.
    this.name = 'I am Class';
    this.message = msg;

    // this를 사용하지 않은 변수
    message2 = "find me!";
    // 멤버함수 선언
    this.print = function(){
        console.log(message2);
    };
}

// 객체를 생성
var myClazz = new Clazz('good to see u!');
console.log(myClazz.message);
// this를 사용하지 않은 message2 변수는 외부에서 참조할 수 없다.
console.log(myClazz.message2);
// this로 선언된 함수를 통해 사용할 수 있다.
myClazz.print();
//node test_class 명령어로 위의 코드를 실행하면 함수 내부에 this 로 정의되지 않은 변수는 undefined 로 찾을 수 없다고 나타납니다.
```

이렇게 함수를 new 연산자를 이용해 초기화함으로써 마치 객체처럼 사용할 수 있게 됩니다.
이제 위의 코드에 prototype 예약어를 이용해서 Clazz 외부에서 함수를 추가해 보겠습니다.
classPrototype.js 파일을 하나 만들고 아래 코드를 입력합니다. this 로 선언되지 않은 변수는 prototy[e으로 추가한 함수에서는 참조할 수 없는것을 확인할 수 있습니다.
```javascript
//test_prototype.js
function Clazz(msg){
    this.name = 'I am Class';
    this.message = msg;

    message2 = "find me!";
}
//Clazz 객체의 prototype 을 가져와서 message값을 리턴하는 함수를 하나 추가한다.
Clazz.prototype.getMessage = function(){
    return this.message;
}

Clazz.prototype.getMessage2 = function(){
    return this.message2;
}

// 객체를 생성
var myClazz = new Clazz('good to see u!');
console.log(myClazz.getMessage());
// 내부에 선언된 함수와는 다르게 prototype으로 선언한 함수는 값을 사용할 수 없다.
console.log(myClazz.getMessage2());
```

이제 node.js 에서 객체를 사용하는 방법을 알아보겠습니다. node.js에서는 require 를 사용해서 파일 전체를 객체로 불러올 수 있습니다. 파일 내부의 멤버들은 exports로 정의할 수도 있고, javascript에서처럼 this나 prototype으로 선언하고 module.exports 명령어로 한번에 처리할 수도 있습니다. 먼저 NodeClass.js 파일을 하나 만들고 아래와 같이 입력합니다.
```javascript
//NodeClass.js
function Clazz(){
    this.name = 'Hello there!';
    this.message;
}

// message 변수에 값을 입력하는 함수
Clazz.prototype.setMessage = function(msg){
    this.message = msg;
}
// message 변수의 값을 가져오는 함수
Clazz.prototype.getMessage = function(){
    return this.message;
}

// exports 명령어를 사용함으로써 다른파일에서 require 예약어를 이용해 Clazz 객체를 사용할 수 있게된다.
// exports 명령어의 위치는 파일의 어떤곳에 위치해도 동일하게 동작한다.
module.exports = Clazz;
```

이제 NodeClass 파일을 호출해서 사용할 use_class.js 파일을 생성하고 아래와 같이 입력합니다.
```javascript
//use_class.js
// NodeClass 를 선언한다. 여기서 NodeClass 는 변수명이 아니라 class명 이므로 첫글자를 대문자로 한다.
var NodeClass = require('./NodeClass');

// new 연산자를 사용해서 NodeClass 클래스를 nodeClass 변수로 초기화한다.
var nodeClass = new NodeClass();

// setMessage 함수로 값을 입력한다.
nodeClass.setMessage('Good to See u!');

// 입력된 값을 출력한다.
console.log(nodeClass.getMessage());
// node use_class 명령어로 실행하면 아래와 같이 입력한 메시지가 출력되는 것을 확인할 수 있습니다.
```
