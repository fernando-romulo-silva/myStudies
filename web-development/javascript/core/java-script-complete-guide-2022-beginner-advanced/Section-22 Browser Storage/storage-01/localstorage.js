const storeBn = document.getElementById('store-btn');
const retrBt = document.getElementById('retrieve-btn');

const userId = 'u123';
const user = {
    name: 'Max',
    age: 30,
    hobbies: ['Sports', 'Cooking']
}

storeBn.addEventListener('click', ()=> {
    sessionStorage.setItem('uid', userId);
    localStorage.setItem('user', JSON.stringify(user));
});

retrBt.addEventListener('click', ()=> {
    const extractedId = sessionStorage.getItem('uid');
    
    const extractedUser = JSON.parse(localStorage.getItem('user'));
    console.log(extractedUser);

    if (extractedId) {
        console.log(`Got the id - ${extractedId}`);
    } else {
        console.log('Could not find id.');
    }
});