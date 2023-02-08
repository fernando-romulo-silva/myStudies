const storeBn = document.getElementById('store-btn');
const retrBt = document.getElementById('retrieve-btn');

const dbRequest = indexedDB.open('StorageDummy', 1);

let db;

dbRequest.onsuccess = function(event) {
    db = event.target.result;
};

dbRequest.onerror = function(event) {
    console.log('ERROR!');
};

dbRequest.onupgradeneeded = function(event) {

    db = event.target.result;

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
}

storeBn.addEventListener('click', ()=> {

    if (!db) {
        return;
    }
  
    const productsStore =  db
        .transaction('products', 'readwrite')
        .objectStore('products');

    productsStore.add({
        id: 'p2', 
        title: 'A Second Product', 
        price: 112.99, 
        tags:['Expansive', 'Luxury']
    });

});

retrBt.addEventListener('click', ()=> {

    const productsStore = db
        .transaction('products', 'readwrite')
        .objectStore('products');

    const request = productsStore.get('p2');

    request.onsuccess = function() {
        console.log(request.result);
    }

});