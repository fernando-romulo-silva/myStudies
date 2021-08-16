const person = {};
person.firstName = 'John';

person['firstName'] = 'Smith';

person.firstName = 'Bob';


person.son = {
    name: 'Nick'
};

person.son.age = 5;

console.log(person);

console.log(person.firstName);


const person2 = {
    myFunc() {
        console.log('Hi there');
    }
}

person2.myFunc();
