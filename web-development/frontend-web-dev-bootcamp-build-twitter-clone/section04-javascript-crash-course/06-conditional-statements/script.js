const kid = 'Alexis';
const gender = 'female';

if (gender === 'male') {
    console.log(kid + ' is my son');
} else {
    console.log(kid + 'is my daughter');
}


const prof = 'developer';

if (prof === 'instructor') {
    console.log(prof + ' teaches students');
} else if (prof === 'compositor') {
    console.log(prof + ' createes music');
} else {
    console.log('Professions do not match')
}


if (5 === 5 && 4 === 4) {
    console.log('Conditions is true');
} else {
    console.log('Condition is false');
}

if (5 === 3 || 4 === 2) {
    console.log('Conditions is true');
} else {
    console.log('Condition is false');
}