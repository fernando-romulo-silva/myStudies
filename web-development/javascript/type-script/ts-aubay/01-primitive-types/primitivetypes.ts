// Boolean
let isThisVideoDone: boolean = false;

// Number
let myNumber: number = 4;

// Text, string
let myName: string = 'Fernando';

// template strings
let greeting: string = 'Hello, '+ myName;
let greeting2: string = `Hello ${myName}`;

// Arrays
let count: Array<number> = [1, 2, 3, 4, 5, 6];
let count2: number[] = [1, 2, 3, 4, 5, 6];

// If you don't know the type 
// watch out for this!
// It's easy to use this a log, but you'll miss out on type checking!
let anything: any = 4;
anything = 'Some text';
anything = false;

// Returning nothing
function gretter(name: string, age: number) : void {
    console.log(`Hello ${name}, you are ${age} old!`);    
}

// Enums
let joystickStatus = 1;

enum Directions { UP, DOWN, LEFT, RIGHT };

if (joystickStatus === Directions.UP) {

}

// Param types
function gretterNew( user: { name: string, age: number } ) : void {
    console.log(`Hello ${user.name}, you are ${user.age} old!`);    
}

