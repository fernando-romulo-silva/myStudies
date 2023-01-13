// import 'core-js';

const button = document.querySelector('button');
const textParagraph = document.querySelector('p');

button.addEventListener('click', () => {
  const text = textParagraph.textContent;
  
  if (navigator.clipboard){
    
    navigator.clipboard.writeText(text)
    .then(result => {
      console.log(result);
    })
    .catch(error => {
      console.log(error);
    });

  } else {
    alert('Feature not available, please copy manually!');
  }

});