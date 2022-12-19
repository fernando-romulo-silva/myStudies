console.log(document.cookie);

const storeBn = document.getElementById('store-btn');
const retrBt = document.getElementById('retrieve-btn');

const userId = 'u123';
const user = {
    name: 'Max',
    age: 30,
    hobbies: ['Sports', 'Cooking']
}

storeBn.addEventListener('click', ()=> {
    document.cookie = `uid=${userId}; max-age=360`;
    document.cookie = `user=${JSON.stringify(user)}`;
});

retrBt.addEventListener('click', ()=> {
    const cookieData = document.cookie.split(';');
    const data = cookieData.map(i => {
        return i.trim();
    });

    console.log(data);

});