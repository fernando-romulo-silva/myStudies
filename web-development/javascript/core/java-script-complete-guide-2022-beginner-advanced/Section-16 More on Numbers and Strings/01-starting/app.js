function randomIntBetween(min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min);
}

console.log(randomIntBetween(1, 10));

function productDescription(strings, productName, productPrice) {

    console.log(strings);
    console.log(productName);
    console.log(productPrice);

    if (productPrice > 20) {
        priceCategory = 'fair';
    }

    // return `${strings[0]}${productName}${strings[1]}${priceCategory}${strings[2]}`;
    return {name: productName, price: prodPrice};
}

const prodName = 'JavaScript Course';
const prodPrice = 29.99;

// tag
const productOutput = productDescription`This proudct (${prodName}) is ${prodPrice}.`;

console.log(productOutput);


//-------------------------------------------------------

const regex = /^\S+@\S+\.\S+$/;

const testResult = regex.test('test@test.com');

console.log(testResult);


const regexNew1 = new RegExp('hello');

console.log(regexNew1.test('Hi there, hello'));

const regexNew2 = new RegExp('(h|H)ello');

console.log(regexNew2.test('Hi there, Hello'));