"use strict";
class UserOld {
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }
}
class User {
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }
    print() {
        console.log(this);
    }
}
class Admin extends User {
    constructor(name, age, permissions) {
        super(name, age);
        this.permissions = permissions;
    }
}
const user = new User('Fer', 30);
console.log(user.name);
const num1Input = document.getElementById('num1');
const num2Input = document.getElementById('num2');
const num3Input = document.getElementById('num2');
const buttonElement = document.querySelector('button');
function add(a, b) {
    return a + b;
}
var OutputMode;
(function (OutputMode) {
    OutputMode[OutputMode["CONSOLE"] = 0] = "CONSOLE";
    OutputMode[OutputMode["ALERT"] = 1] = "ALERT";
})(OutputMode || (OutputMode = {}));
;
function printResult(result, printMode) {
    if (printMode === OutputMode.CONSOLE) {
        console.log(result);
    }
    else if (printMode === OutputMode.ALERT) {
        alert(result);
    }
}
function printResult2(result, printMode) {
    if (printMode === 'console') {
        console.log(result);
    }
    else if (printMode === 'alert') {
        alert(result);
    }
}
const results = [];
const names = ['Fer'];
buttonElement.addEventListener('click', () => {
    const num1 = +num1Input.value;
    const num2 = +num2Input.value;
    const result = add(num1, num2);
    const resulContainer = {
        res: result,
        print() {
            console.log(this.res);
        }
    };
    results.push(resulContainer);
    resulContainer.res;
    printResult(results, OutputMode.CONSOLE);
    printResult(results, OutputMode.ALERT);
    results[0].print();
});
function logAndEcho(val) {
    console.log(val);
    return val;
}
logAndEcho('Hi there!').split(' ');
