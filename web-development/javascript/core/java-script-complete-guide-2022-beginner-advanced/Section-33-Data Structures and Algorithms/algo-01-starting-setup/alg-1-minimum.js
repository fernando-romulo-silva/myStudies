function getMin(numbers) {
    
    if (!numbers.length) {
        throw new Error('Should not be an empty array!');
    }
    
    if (numbers.length === 1) {
        return numbers[0];
    }

    let currentMinimum = numbers[0];
    console.log('EXECUTION - INIT');
    
    for (let i = 1; i < numbers.length; i++) {
        console.log('EXECUTION - FOR');
        if (numbers[i] < currentMinimum) {
            currentMinimum = numbers[i];
        }
    }


    console.log('EXECUTION - RETURN');
    return currentMinimum;
}

function getMin2(numbers) {
    
    if (!numbers.length) {
        throw new Error('Should not be an empty array!');
    }

    for (let i = 0; i < numbers.length; i++) {

        let outerElement = numbers[i];

        for (let j = i + 1; j < numbers.length; j++) {

            let innerElement = numbers[j];

            if (outerElement > innerElement) {
                // swap
                numbers[i] = innerElement;
                numbers[j] = outerElement;

                outerElement = numbers[i];
                innerElement = numbers [j];

                console.log(numbers[i], numbers[j]);
            }
        }
    }

    return numbers[0];
}

const testNumbers = [3, 1, 2, -5];

const mim = getMin(testNumbers);

console.log(mim);