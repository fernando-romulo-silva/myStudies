const newEl = document.createElement('li');
console.log(newEl);

const text = document.createTextNode('Blog');
console.log(text);

newEl.appendChild(text);
console.log(newEl);

const list = document.querySelector('.list');
list.appendChild(newEl);

list.insertBefore(newEl, list.children[0]);


list.removeChild(newEl)
