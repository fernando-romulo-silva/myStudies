const h1 = document.getElementById('main-title');
h1.textContent = 'Some new title!';
h1.style.color = 'white';
h1.style.backgroundColor = 'black';

const li = document.querySelector('li:last-of-type');
li.textContent = li.textContent + ' (Changed!)';

const body = document.body;

// const listItemElements = document.querySelector('li');
const listItemElements = document.getElementsByName('li');

// for (const listItemEl of listItemElements) {
//     console.log(listItemEl);
// }

const ul = document.body.firstElementChild.nextElementSibling;

const firstLi = ul.firstElementChild;

console.log(firstLi);