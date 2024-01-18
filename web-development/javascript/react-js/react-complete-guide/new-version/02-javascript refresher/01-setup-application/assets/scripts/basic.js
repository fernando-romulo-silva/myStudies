import { apiKey, abc as content } from './util';

import * as util from './util';

import defaultValue from './config';

console.log(apiKey, content);
console.log(defaultValue);

console.log('object import:', util);

// --------------------------------------------------

let userMessage = 'Hello World!';

console.log(userMessage);

userMessage = 'Hello World!!!';

console.log(userMessage);

const userMessageConst = 'My const!';
console.log(userMessageConst);

// --------------------------------------------------

console.log(10 / 5);
console.log('Fernando' + ' ' + 'Silva');

if (10 === 10) {
  console.log('works');
}

// --------------------------------------------------

function createGreeting(userName, message = 'Hello!') {
  return 'Hi, I am ' + userName + '. ' + message;
}

const result1 = createGreeting('Fernando');
console.log(result1);

createGreeting('Romulo', "Hello, what's up?");

// --------------------------------------------------

//annonymous function
(userName, message) => {
  console.log('Hello!');
  return userName + message;
};

// --------------------------------------------------

const user = {
  name: 'Fernando',
  age: 42,

  greet() {
    console.log(this.age);
  },
};

console.log(user);
console.log(user.name);

user.greet();

// --------------------------------------------------

class User {
  constructor(name, age) {
    this.name = name;
    this.age = age;
  }

  greet() {
    console.log(this.name);
  }
}

const user2 = new User('John', 23);
console.log(user2);

user2.greet();

// --------------------------------------------------

const hobbies = ['Sports', 'Cooking', 'Reading'];
console.log(hobbies[1]);

hobbies.push('working');

console.log(hobbies);

const resultIndex = hobbies.findIndex((item) => item === 'Reading');
console.log(resultIndex);

const mapResult = hobbies.map((item) => ({ text: item }));
console.log(mapResult);

// --------------------------------------------------

const userNameData = ['Fernando', 'Romulo', 'da Silva'];

const [firstName] = userNameData;

console.log(firstName);

// --------------------------------------------------

const userNew = {
  name: 'Fernando',
  age: 42,
};

const { name: userName } = userNew;

console.log(userName);

const order1 = { id: 1, currency: 'USD' };

function storeOrder1(order) {
  localStorage.setItem('id', order.id);
  localStorage.setItem('currency', order.currency);
}

function storeOrder2({ id, currency }) {
  // destructuring
  localStorage.setItem('id', id);
  localStorage.setItem('currency', currency);
}

// --------------------------------------------------
// spread

const myHobbies = ['Sports', 'Cooking'];
const newHobbies = ['Reading'];

const mergedHobbies = [...newHobbies, ...myHobbies];

const userNewAgain = {
  name: 'Fernando',
  age: 42,
};

const extendedUser = {
  isAdmin: true,
  ...userNewAgain,
};

// --------------------------------------------------

for (const hobby of mergedHobbies) {
  console.log(hobby);
}
