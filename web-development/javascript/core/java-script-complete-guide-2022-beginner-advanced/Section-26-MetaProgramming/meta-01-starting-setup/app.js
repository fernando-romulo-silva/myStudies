// Library land

const uid = Symbol('uid');
console.log(uid);

const user = {
    // id: 'p1',
    [uid]: 'p1',
    name: 'Max',
    age: 30,
    [Symbol.toStringTag]: 'User Object'
};

// App land => Using the library

user.id = 'p2'; // this should not be possible!

console.log(user);

console.log(user[Symbol('uid')]); // total diferent object

console.log(Symbol('uid') == Symbol('uid'));

console.log(user);

const company = {
    curEmployee: 0,
    employees: ['Max', 'Manu', 'Anna'],
    next() {
        if (this.curEmployee >= this.employees.length) {
            return { value: this.curEmployee, done: true };
        }
        const returnValue = { value: this.employees[this.curEmployee], done: false};
        this.curEmployee++;
        return returnValue;
    },
    [Symbol.iterator]: function* employeeGenerator() {
        // 1
        // let employee = company.next();
        // while (!employee.done) {
        //     yield employee.value; // 
        //     employee = company.next();    
        // }

        let currentEmployee = 0;
        while (currentEmployee < this.employees.length) {
            yield this.employees[currentEmployee];
            currentEmployee++;
        }
    }
};
// 0
// let employee = company.next();
// while (!employee.done) {
//     console.log(employee.value);
//     employee = company.next();    
// }

// 1
for(const employee of company){
    console.log(employee);
}

console.log([...company]);

// =============================================================================

const course = {
    title: 'JavaScript - The Complete Guide'
};

Reflect.setPrototypeOf(course, {
    toString() {
        return this.title;
    }
});

Reflect.defineProperty(course, 'price', {value:20});

console.log(course);

Reflect.deleteProperty(course, 'title');
//delete course.title;

console.log(course);

// =============================================================================

const courseHandler = {
    get (object, propertyName) {
        console.log(propertyName);

        if (propertyName === 'length') {
            return 0;
        }

        return object[propertyName] || 'NOT FOUND';
    },

    set (object, propertyName, newValue) {

        if (propertyName === 'rating') {
            return;
        }

        object[propertyName] = newValue;
    }
};

const pCourse = new Proxy(course, courseHandler);

console.log(pCourse.price);
pCourse.rating = 5;

console.log(pCourse);


console.log(pCourse.length);
