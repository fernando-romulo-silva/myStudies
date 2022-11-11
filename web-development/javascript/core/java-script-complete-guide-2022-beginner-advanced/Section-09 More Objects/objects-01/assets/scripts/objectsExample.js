
const movieList = document.getElementById('movie-list');

movieList.style.backgroundColor = 'red';
movieList.style['background-color'] = 'red';

movieList.style.display = 'block';

const userChosenKeyName = 'level';

let person = {
    'first-name': 'Max',
    age: 30,
    gender: 'Male',
    hobbies: ['Sports', 'Cooking'],
    [userChosenKeyName]: '...',
    greet: function() {
        alert('Hi there!');
    },
    1.5: 'hello'
};

person.isAdmin = true;

console.log(person);

delete person.age;

console.log(person); // no age anymore

//-------------------------------------------------------------------------------------

console.log(person['first-name']);

console.log(person[1.5]);

//-------------------------------------------------------------------------------------


//-------------------------------------------------------------------------------------

const person2 = { ... person, hair: 'black' };

console.log(person2);

// spread operator
const {hobbies, gender} = { ...person };
console.log(hobbies, gender);