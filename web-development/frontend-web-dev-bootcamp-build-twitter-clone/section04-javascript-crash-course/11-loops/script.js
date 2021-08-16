const arr = ['John', 'Nick', 'Bob', 'Michael', 'Mary'];


// console.log(arr[0]);
// console.log(arr[1]);

for (let i = 0; i < arr.length; i++) {
    if (arr[i] === 'Bob') {
        console.log(arr[i] + ' is my brother');
        // break;
        continue;
    }
    
    console.log(arr[i]);
}


let j = 0;

while (j <= 10) {
    
    j++;
    console.log(j);
}

j = 0;

do {
    
    console.log(j);

    j++;

} while (j < 10);

