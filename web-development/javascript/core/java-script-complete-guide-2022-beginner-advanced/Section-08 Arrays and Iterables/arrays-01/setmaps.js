const ids = new Set([1, 2, 3]);
console.log(ids);

ids.add(2);
console.log(ids.has(1));
console.log(ids);

ids.delete(2);

for (const entry of ids.entries()) {
    console.log(entry[0]);
    // console.log(entry.values);
}

// ----------------------------------------------------

const person1 = {name: 'Max'};
const person2 = {name: 'Manuel'};

const personData = new Map([[person1, [{date: 'yesterday', price: 10}]]]);
console.log(personData);

console.log(personData.get(person1));

personData.set(person2, [{ date: 'two weeks ago', price: 100 }]);

console.log(person2);

for (const [key, value] of personData.entries()) {
    console.log(key, value);
}

for (const key of personData.keys()) {
    console.log(key);
}

for (const value of personData.values()) {
    console.log(value);
}

// --------------------------------------------------

let person = {name: 'Max'};
const personsWeak = new WeakSet();

personsWeak.add(person);

person = null;

console.log(personsWeak);

// --------------------------------------------------

person = {name: 'Max'};
const personWeakMap = new WeakMap();

personWeakMap.set(person, 'Extra info');

person = null;

console.log(personWeakMap);


