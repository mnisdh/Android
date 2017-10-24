// 1. 변수의 선언 : var 변수명
var a = 10;
var b = 5;


// 2. 콘솔에 로그출력 : console.log("")
console.log(a);
console.log(b);


// 3. 반복문 : for(var i = 0; i < 10; i++){}
for (var i = 0; i < 10; i++) {
	console.log(i);
}
    // 일반 for문은 배열의 index를 리턴
    //for(item in array){
    //    response.write("[" + array[item] + "]");
    //}

    
    // 향상된 for문은 배열안의 실제 item을 리턴
    // array.forEach(function(item){
    //     response.write("[" + item + "] </br>");
    // });



// 4. 조건문 : if(비교){ } else if(비교){ } else{ }
if (a > 10) {
	console.log("a가 10보다 큽니다");
}
else if (a < 10) {
	console.log("a가 10보다 작습니다");
}
else {
	console.log("a와 10은 같습니다");
}


// 5. 문자열 기본연산 : "문자열" + "문자열" = "문자열문자열"
var c = "dkf" + "asdf";
console.log(c);


// 6. 함수사용하기
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



// 7. class 사용하기
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


// javascript의 object
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