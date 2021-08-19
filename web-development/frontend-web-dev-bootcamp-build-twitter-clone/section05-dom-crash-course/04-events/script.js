const heding = document.querySelector('.heading');
const btn = document.querySelector('.btn');

btn.onclick = () => {
    console.log('Clicked');
};

btn.onmouseover = () => {

    heading.style.backgroundColor = 'green';
};

btn.onmouseout = () => {
    heading.style.backgroundColor = 'transparent';
};

function clickMe() {
    console.log('Clicked');
}


const btn3 = document.querySelector('.btn3');

btn3.addEventListener('click', () => {
    heading.style.cssText = 'background-color: brown; color: white';
});

const btn4 = document.querySelector('.btn4');
btn4.addEventListener('click', (e) => {
    console.log(e);

    console.log(e.target);
});