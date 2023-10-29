// Primitives: number, tring, boolean
// More complex types: arrays, objects
// Function types, parameters

// Primitives
let age: number = 12;

let userName: string;
userName = 'Fer';

let isInstructor: boolean;
isInstructor = false;

// More complex types

let hobbies: string[];
hobbies = ['Sports', 'Cooking'];

type Person = { name: string; age: number };

let person: Person;
person = {
  name: 'Fer',
  age: 41,
};

let people: Person[];

// person = {
//   isEmployee: true,
// };

// Type Inference
let course: string | number = 'React - The Complet Guide';

course = 1234;

// Functions & types

function add(a: number, b: number): number {
  return a + b;
}

function print(value: any): void {
  console.log(value);
}

// Generics

function insertAtBeginning<T>(array: T[], value: T): T[] {
  const newArray = [value, ...array];
  return newArray;
}

const demoArray = [1, 2, 3];

const updatedArray = insertAtBeginning(demoArray, -1); // [ -1, 1, 2, 3]

insertAtBeginning(['a', 'b'], 'c');
