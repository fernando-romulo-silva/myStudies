const multiply = function(x, y) {
    const a = x * y;
    return a;
};

console.log(multiply(10, 5));

//---------------------------------------------------

const multiply_new = (x, y) => {
    const a = x * y;
    return a;
};

console.log(multiply_new(10, 5));

//---------------------------------------------------

const multiply_new_2 = (x, y) => x * y;

console.log(multiply_new_2(10, 5));

//---------------------------------------------------

const multiply_new_3 = x => x * x;

console.log(multiply_new_3(10));


//---------------------------------------------------

const multiply_new_4 = () => 2 * 3;

console.log(multiply_new_4());
