const storeBn = document.getElementById('store-btn');
const retrBt = document.getElementById('retrieve-btn');


const dbRequest = indexedDB.open('StorageDummy', 1);

dbRequest.onupgradeneeded = function(event) {
    const db = event.target.result;

    const objStore = db.createObjectStore('products', {keyPath: 'id'});
    
    objStore.transaction.oncomplete = function(event) {
        const productsStore =  db.transaction('products', 'readwrite').objectStore('products');

        productsStore.add({
            id: 'p1', 
            title: 'A First Product', 
            price: 12.99, 
            tags:['Expansive', 'Luxury']
        });
    }
};

dbRequest.onerror = function(event) {
    console.log('ERROR!');
};

const userId = 'u123';
const user = {
    name: 'Max',
    age: 30,
    hobbies: ['Sports', 'Cooking']
}

storeBn.addEventListener('click', ()=> {


});

retrBt.addEventListener('click', ()=> {

    console.log(data);

});