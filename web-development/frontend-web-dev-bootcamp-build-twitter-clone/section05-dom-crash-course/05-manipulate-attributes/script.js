const paragraph = document.querySelector('.paragraph');

console.log(paragraph.getAttribute('id'));
console.log(paragraph.getAttribute('class'));
console.log(paragraph.getAttribute('title'));


paragraph.setAttribute('style', 'background-color: coral');

paragraph.removeAttribute('style');

console.log(paragraph.hasAttribute('style'));
console.log(paragraph.hasAttribute('class'));
