const REQUIRED = 'REQUIRED';
const MIN_LENGTH = 'MIM_LENGTH';

function validate(value, flag, validatorValue) {
    if (flag === REQUIRED) {
        return value.trim().length > 0;
    }

    if (flag === MIN_LENGTH) {
        return value.trim().length > validatorValue;
    }
}

function createUser(userName, userPassword) {
    if (!validate(userName, REQUIRED) || !(validate(userPassword, MIN_LENGTH, 5))) {
        throw new Error(
            'Invalid input - username  or password is wrong (password should be at least six characters)'
        );
    }

    return {
        userName: userName,
        password: userPassword
    }
}

function getUserInput(inputElementId) {
    return document.getElementById(inputElementId).value;
}

function greetUser(user){
    console.log('Hi, I am '+ user.userName);
}

function signupHanlder(event) {
    event.preventDefault();

    const enteredUsername = getUserInput('username');    
    const enteredPassword = getUserInput('password');

    try {
        const newUser = createUser(enteredUsername, enteredPassword);
        console.log(newUser);
        greetUser(newUser);
    } catch (error) {
        alert(error.message);
    }

}

function connectForm(formId, formSubmitHandler) {
    const form = document.getElementById(formId);
    form.addEventListener('submit', formSubmitHandler);
}

connectForm('user-input', signupHanlder);