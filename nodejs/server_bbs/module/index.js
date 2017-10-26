// public
exports.a = 213;

// private
var b = 542;

function sum2(a, b){
    return a+b;
}

exports.sum = function(a, b){
    return a+b;
};