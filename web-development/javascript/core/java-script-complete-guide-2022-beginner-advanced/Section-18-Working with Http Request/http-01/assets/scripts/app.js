
const listElement = document.querySelector('.posts');
const postTemplate = document.getElementById('single-post');
const form = document.querySelector('#new-post form');
const fetchButton = document.querySelector('#available-posts button');
const postList = document.querySelector('ul');

function sendHttpRequestOld(method, url, data) {

    const promise = new Promise ((resolve, reject) => {
        const xhr = new XMLHttpRequest();

        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.open(method, url); // conf request
        
        xhr.responseType = 'json';
        
        xhr.onload = function() {
            if (xhr.status >= 200 && xhr.status < 300) {
                resolve(xhr.response);
            } else {
                reject(new Error('Something went wrong!'));
            }
        };

        xhr.onerror = function() {
            reject(new Error('Failed to send request!'));
        };
        
        xhr.send(JSON.stringify(data));
    });

    return promise;
}

function sendHttpRequest(method, url, data) {

    return fetch(url, {
        method: method,
        // headers: {
        //     'Content-Type': 'application/json',
        // },
        // body: JSON.stringify(data)
        body: data
    }).then(response => {
        
        if (response.status >= 200 && response.status < 300) {            
            return response.json();
        } else {
            return response.json().then(errData => {                
               throw new Error('Something went wrong - server-side')
            });
        }

    }).catch(response => {
        throw new Error('Something went wrong!');
    });
}

async function fetchPosts () {

    try {
        
        const responseData = await sendHttpRequest(
            'GET', 
            'https://jsonplaceholder.typicode.com/posts'
        );
            
       // const listOfPost = JSON.parse(xhr.response);
       
        for (const post of responseData) {
            const postEl = document.importNode(postTemplate.content, true);
            postEl.querySelector('h2').textContent = post.title.toUpperCase();
            postEl.querySelector('p').textContent = post.body;
            postEl.querySelector('li').id = post.id;
    
            listElement.append(postEl);
        }           

    } catch (error) {
        alert(error.message);
    }   
}

async function createPost(title, content) {
    const userId = Math.random();
    const post = {
        title: title,
        body: content,
        userId: userId
    };

    const fd = new FormData(form);
    // fd.append('title', title);
    // fd.append('body', content);
    fd.append('userId', userId);
    // fd.append('file', 'file.png');

    sendHttpRequest('POST', 'https://jsonplaceholder.typicode.com/posts', fd);
}

fetchButton.addEventListener('click', fetchPosts);

form.addEventListener('submit', event =>{
    event.preventDefault();
    const enteredTitle = event.currentTarget.querySelector('#title').value;
    const enteredContent = event.currentTarget.querySelector('#content').value;
    
    createPost(enteredTitle, enteredContent);
});

postList.addEventListener('click', event => {
    if (event.target.tagName === 'BUTTON') {
        const postId = event.target.closest('li').id;

        sendHttpRequest('DELETE', `https://jsonplaceholder.typicode.com/posts/${postId}`);

    }
});

