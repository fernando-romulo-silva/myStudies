const numbers = [1, 2, 3];
console.log(numbers);

const moreNumbers = new Array(1, 2, 3); // [1, 2, 3]
const moreNumbers02 = Array(5); // five empty positions
console.log(moreNumbers);

const yetMoreNumbers = Array.of(1, 2);
console.log(yetMoreNumbers);

const moreNumbers03 = Array.from('Hello');
console.log(moreNumbers03);

const listItems = document.querySelectorAll('li');
console.log(listItems);

const moreNumbers04 = Array.from(listItems);
console.log(moreNumbers04);

let hobbies = ['Cooking', 'Sports'];
const personalData = [30, 'Max', {moreeDetail: []}];

const analyticsData = [[1, 1.6], [-5.4, 2.1]];

for (const data of analyticsData) {
    for (const dataPoints of data) {
        console.log(dataPoints);
    }
}

console.log(personalData[1]);

// -------------------------------------------------

hobbies.push('Reading'); // at the end of the array
console.log(hobbies);

hobbies.unshift('Coding'); // first
console.log(hobbies);

const poppedValue = hobbies.pop();
console.log(hobbies);
console.log(poppedValue);

const shiftedValue = hobbies.shift();
console.log(hobbies);
console.log(shiftedValue);

hobbies[1] = 'Coding Again';
console.log(hobbies);

hobbies[5] = 'Watch Movies'; // rarely used

// -------------------------------------------------

hobbies = ['Cooking', 'Sports'];

hobbies.splice(1, 0, 'Good Food');
console.log(hobbies);

hobbies.splice(0, 1);
console.log(hobbies);

// -------------------------------------------------

const testResults = [1, 5.3, 1, 1.5, 10.99, -5, 10];
const storedResults = testResults.slice(0, 3);

testResults.push(9);

console.log(storedResults, testResults);

// --------------------------------------------------

const storedResultsNew = testResults.concat([23, 34, 34]);
console.log(storedResultsNew, testResults);

// --------------------------------------------------

console.log(testResults.indexOf(1));
console.log(testResults.lastIndexOf(1));

// --------------------------------------------------

const personData = [{name: 'Max'}, {name: 'Manuel'}];
console.log(personData.indexOf({name: 'Manuel'})); // -1

const manuel = personData.find((person, index, persons) => {
    return person.name === 'Manuel';
});

console.log(manuel);

const maxIndex = personData.findIndex((person, index, persons) => {
    return person.name === 'Max';
});

console.log(maxIndex);

// --------------------------------------------------

const prices = [10.99, 5.99, 3.99, 6.59];

const tax = 0.19;

let taxAdjustedPrices = [];

// for (const price of prices) {
//     taxAdjustedPrices.push(price * (1 + tax));
// }

prices.forEach((price, index, prices) => {
    const priceObject = { idx: index, taxAdjPriice: price * (1 + tax)};

    taxAdjustedPrices.push(priceObject);
});


console.log(taxAdjustedPrices);

// --------------------------------------------------

taxAdjustedPrices.splice(0, taxAdjustedPrices.length)

taxAdjustedPrices = prices.map((price, index, prices) => {
    const priceObject = { idx: index, taxAdjPriice: price * (1 + tax)};
    return priceObject;   
});

console.log(taxAdjustedPrices);

// --------------------------------------------------

const sortedPrices = prices.sort((a, b) => {
    if (a > b ) {
        return 1;
    } else if (a === b) {
        return 0;
    } else {
        return -1;
    }
});

console.log(sortedPrices.reverse());

// --------------------------------------------------

const filteredArray = prices.filter((price, index, prices) => {

    return price > 6;

});

console.log(filteredArray);

// --------------------------------------------------

const filteredArray2 = prices.filter(price => price > 6);

// --------------------------------------------------

const sum = prices.reduce((prev, cur, curIndex, prices) => {
    return prev + cur;
}, 0);

console.log(sum);

// --------------------------------------------------

const dataCities = 'New York;10.99;2000';

const transformedData = dataCities.split(';');
console.log(transformedData);

const nameFragments = ['Max', 'Schwarz'];
const name = nameFragments.join(' ');
console.log(name);

// --------------------------------------------------

const copiedNamedFragments = [...nameFragments];
nameFragments.push('Mr');
console.log(nameFragments, copiedNamedFragments);

console.log(Math.min(...prices)); // don't work withou spread operadora

const persons = [{name: 'Max', age: 30}, {naem: 'Manuel', age: 31}];
const copiedPersons = persons.map(person => ({
    name: person.name,
    age: person.age
}));

persons.push({name: 'Anna', age: 29});

console.log(persons, copiedPersons);

// --------------------------------------------------

const nameData = ['Max', 'Schwarz', 'Mr', 30];

// const firstName = nameData[0];
// const lastName = nameData[1];
const [firstName, lastName, ...otherInformation ] = nameData;

console.log(firstName);
console.log(lastName);
console.log(otherInformation);