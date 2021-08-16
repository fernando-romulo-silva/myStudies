const heading = document.getElementById('heading');
heading.style.color = 'red';

// heading.style.backgroundColor = 'green';
heading.className = 'changeBG';
// heading.className = 'changeFT';
 heading.className += ' changeFT';

 heading.classList.add('changeCL');
 
 console.log(heading.classList);

const lis = document.querySelectorAll('ul li');
console.log(lis);

lis[1].style.backgroundColor = 'red';



for(let i = 0; i < lis.length; i++) {
    lis[i].style.backgroundColor = 'royalblue';
}


lis[0].style.cssText = 'background-color: coral; color: white; font-size: 25px;'


