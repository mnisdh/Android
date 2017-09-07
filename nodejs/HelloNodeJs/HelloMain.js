function calcTest(){
  var a = 2 + 3;
  var b = 2 - 3;
  var c = 2 * 3;
  var d = 2 / 3;//소수점이하 반올림
  var e = 5 % 3;//5를 3으로 나눈 나머지
  var f = 10 % 3;//10을 3으로 나눈 나머지
  var dou = 5.0 % 4.2;

  console.log("2 + 3 = " + a);
  console.log("2 - 3 = " + b);
  console.log("2 * 3 = " + c);
  console.log("2 / 3 = " + d);
  console.log("5 % 3 = " + e);
  console.log("10 % 3 = " + f);
  console.log("5.0 % 4.2 = " + dou);
}

calcTest();
