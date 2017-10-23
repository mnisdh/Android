var count = 100;

// 함수를 만들고
// 0 시작해서 100개의 피보나치 수열을 출력하세요
function getFibonacci(cnt) {
    var before = 0, current = 0;
    for (var i = 0; i < cnt; i++) {
        if (i === 0) {
            console.log(i);
            before = i;
        }
        else if (i === 1) {
            console.log(i);
            current = i;
        }
        else {
            var temp = before + current;
            before = current;
            current = temp;
            console.log(current);
        }
    }
}

calc(count);