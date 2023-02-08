class UserOld {
  name: string;
  private age: number;

  constructor(name: string, age: number) {
    this.name = name;
    this.age = age;
  }
}

interface Greetable {
  name: string;
}

interface Printable {
  print() : void;
}

class User implements Greetable, Printable {
  constructor(public name: string, private age: number) {}
  
  print(): void {
    console.log(this);
  }
}

class Admin extends User {
  constructor(name: string, age: number, private permissions: string[]) {
    super(name, age);
  }
}

const user = new User('Fer', 30);
console.log(user.name);


const num1Input = document.getElementById('num1') as HTMLInputElement;
const num2Input : HTMLInputElement = document.getElementById('num2');
const num3Input = <HTMLInputElement>document.getElementById('num2');

const buttonElement = document.querySelector('button');

function add(a: number, b: number): number {
  return a + b;
}

enum OutputMode { CONSOLE, ALERT };

function printResult(result: any, printMode: OutputMode): void {

  if (printMode === OutputMode.CONSOLE) {
    console.log(result);
  } else if (printMode === OutputMode.ALERT) {
    alert(result);
  }

}


function printResult2(result: any, printMode: 'console' | 'alert'): void {

  if (printMode === 'console') {
    console.log(result);
  } else if (printMode === 'alert') {
    alert(result);
  }

}

// const result: number = add(5, 3);

// let isDone = false;

// printResult(result);

interface CalculationsContainer {
  res: number;
  print(): void; 
}

type CalculationResults = CalculationsContainer[];

type CalculationResults2 = { res: number, print: () => void }[];

const results: Array<CalculationResults> = [];

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


function logAndEcho<T>(val: T) {
  console.log(val);
  return val;  
}

logAndEcho<string>('Hi there!').split(' ');