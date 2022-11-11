class AgedPerson {  

    printAge() {
        console.log(this.age);
    }
}

class Person extends AgedPerson {
    name = 'Max';

    constructor() {
        super();
        this.age = 30;
    }

    greet() {
        console.log('Hi, I am '+ this.name + ' and I am '+ this.age +' years old');
    }
}

const person = new Person();
person.greet();

console.log(person);
console.log(person.__proto__);

console.log(person.__proto__ === Person.prototype);

// ---------------------------------------------------------------

console.log('---------------------------------------------------------------');

function Person2() {
    this.age = 30;
    this.name = 'Max';
    
    this.greet = function () {
        console.log('Hi, I am '+ this.name + ' and I am '+ this.age +' years old');
    };
}

const person2 = new Person2();
person2.greet();

console.log(person2.toString());

console.log(person2);

console.log(person2.__proto__);

console.log(person2.__proto__ === Person2.prototype);

// Person2.prototype = { // replace
//     printAge() {
//         console.log(this.age);
//     }
// };

Person.prototype.printAge = function() {
    console.log(this.age);
}

console.dir(Person2);

const button = document.getElementById('btn');
button.addEventListener('click', person.greet);

// ----------------------------------------------------------

const course = {
    title: 'JavaScript - The Complete Guide',
    rating: 5
}

console.log(course.__proto__);

// course.printRating();