const button = document.querySelector('button');

// button.onclick = function() {};

const buttonClickHandler = event => {
//    event.target.disable = true;
   console.log(event);
}

const anotherButtonClickHandler = () => {
    console.log('This was clicked!');
}

// button.onclick = buttonClickHandler;
// button.onclick = anotherButtonClickHandler;

// button.addEventListener('click', buttonClickHandler);
// setTimeout(() => {
//     button.removeEventListener('click', buttonClickHandler);
// }, 2000);

// buttons.forEach(btn => {
//     btn.addEventListener('mouseenter', buttonClickHandler);
// });
    
// window.addEventListener('scroll', event => {
//     console.log(event);
// });

const form = document.querySelector('form');

form.addEventListener('submit', event => {
    event.preventDefault();
    console.log(event);
});

const div = document.querySelector('div');
div.addEventListener('click', event => {
    console.log('Clicked Div');
    console.log(event);
});

button. addEventListener('click', event =>{
    event.stopPropagation();
    // event.stopImmediatePropagation();
    console.log('Clicked Button');
    console.log(event);
});
 
const listItems = document.querySelectorAll('li');
const list = document.querySelector('ul');

// listItems.forEach(listItem => {
//     listItem.addEventListener('click', event => {
//         event.target.classList.toggle('highlight');
//     });
// });

list.addEventListener('click', event => {
    event.target.closest('li').classList.toggle('highlight');
    form.submit();
});