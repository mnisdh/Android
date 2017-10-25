# Nodejs 기초


## 변수 선언

```javascript
// var 변수명
var a = 10;
var b = "5";
```


## 로그출력

```javascript
console.log(a);
console.log(b);
```


## 반복문

```javascript
for(var i = 0; i < 10; i++) {
	console.log(i);
}

//일반 for문은 배열의 index를 리턴
for(item in array){
    response.write("[" + array[item] + "]");
}

//향상된 for문은 배열안의 실제 item을 리턴
array.forEach(function(item){
    response.write("[" + item + "] </br>");
});
```


## 조건문

```javascript
//if(비교){ } else if(비교){ } else{ }
if (a > 10) {
	console.log("a가 10보다 큽니다");
}
else if (a < 10) {
	console.log("a가 10보다 작습니다");
}
else {
	console.log("a와 10은 같습니다");
}
```


## 문자열 기본연산

```javascript
//"문자열" + "문자열" = "문자열문자열"
var c = "dkf" + "asdf";
console.log(c);
```


## Function

```javascript
//     파라미터에 타입이 없다
//     문장내에 return 이 있는지 여부에 따라서 결정
// 타입1
function sum(param1, param2) {
	return param1 + param2;
}
// 타입2
var sum2 = function(param1, param2){
	return param1 + param2;
}
var result = sum(a, b);
console.log(result);
```


## Class

```javascript
//     함수 만드는 것과 동일 (클래스일경우 첫글자를 대문자로)
function ClassName(param1) {
	var a = 0; // private 선언된 변수 : 외부에서 접근 안됨
	this.b = 10; // public 선언된 변수 : 외부에서 접근 가능

	this.c = function(param1, param2){

	};
}
// 클래스의 사용
var num = new Num("Hello", 157);
num.b = 500;
num.c("a", 3241);
```


## Object

```javascript
var request = {
	one : "hello",
	two : 123,
	three : {
		one : "hello2",
		two : 1233
	},
	sum : function(){
	   return one + threee;
	}
};
```