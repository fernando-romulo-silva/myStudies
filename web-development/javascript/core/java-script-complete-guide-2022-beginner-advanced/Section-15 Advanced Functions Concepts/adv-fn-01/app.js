// ------------------------------------------------------
// pure functions
function add(num1, num2) { 
   return num1 + num2;
}

console.log(add(1, 5));
console.log(add(12, 15));

// ------------------------------------------------------
// impure functions

function addRandom(num1) { 
    return num1 + Math.random();
}

console.log(addRandom(5));


let previousResult = 0;

function addMoreNumbers(num1, num2) {
    const sum = num1 + num2;
    previousResult = sum; // side effect
    return sum;
}

console.log(addMoreNumbers(1, 5));


const hobbies = ['Sports', 'Cooking'];

function printHobbies(h) {
    h.push('New Hobby'); // side effect
    console.log(h);
}

printHobbies(hobbies);

// ------------------------------------------------------
// factory functions

function calculateTax(amount, tax) {
    return amount * tax;
}

const vatAmount = calculateTax(100, 0.19);
const incomeTax = calculateTax(100, 0.25);

console.log(vatAmount);
console.log(incomeTax);


let multiplier = 1.1;

function createTaxCalculator(tax) {
    
    function internCalculateTax(amount) {
        console.log(multiplier);
        return amount * tax * multiplier;
    }

    return internCalculateTax;
}

const calculateVatAmount = createTaxCalculator(0.19);
const calculateIncomeTax = createTaxCalculator(0.25);

multiplier = 1.2;

console.log(calculateVatAmount(100));
console.log(calculateVatAmount(200));

// ------------------------------------------------------
// closure

let userName = 'Max';

function greetUser() {
    // let name = 'Anna';
    console.log('Hi ' + name);
}

let name = 'Maximilian';

userName = 'Manuel';

greetUser();

// ------------------------------------------------------
// recursion

function powerOfOld(x, n) {
    let result = 1;

    for (let i = 0; i < n; i++) {
        result *= x;
    }

    return result;
}

console.log(powerOfOld(2, 3)); //2 * 2 * 2


function powerOf(x, n) {

    // if (n === 1) {
    //     return x;
    // }

    // return x * powerOf(x, n -1);

    return n === 1 ? x : x * powerOf(x, n - 1);
}

console.log(powerOf(2, 3)); //2 * 2 * 2


// ------------------------------------------------------
// advance recursion

const myself = {
    name: 'Max',
    friends: [
        {
            name: 'Manuel',
            friends: [
                {
                    name: 'Chris',
                    friends: [
                        {
                            name: 'Hari'
                        },
                        {
                           name: 'Amilia' 
                        }
                    ]
                }
            ]

        },
        {
            name: 'Julia'
        }
    ]
};


function getFriendNames(person) {
    const collectedNames = [];

    if (!person.friends) {
        return [];
    }

    for (const friend of person.friends) {
        collectedNames.push(friend.name);
        collectedNames.push(...getFriendNames(friend));
    }

    return collectedNames;
}

console.log(getFriendNames(myself));

