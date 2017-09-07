
var a = 1;
var b = 1.1;
var c = 999999999999999;
var d = "abcd";
var e = [1, 2, 3, 4, 5];

console.log(a);
console.log(b);
console.log(c);
console.log(d);
console.log(e);

//숫자를 문자로 변환
function numberToString(num){
    return a + "";
}
console.log(numberToString(a));


d = "1234.12";
//문자를 숫자로 변환
function stringToInt(str){
  return parseInt(str);
}
console.log(stringToInt(d));

//문자를 숫자로 변환
function stringToFloat(str){
  return parseFloat(str);
}
console.log(stringToFloat(d));

//문자를 숫자로 변환
function stringToNumber(str){
  return Number(str);
}
console.log(stringToNumber(d));
