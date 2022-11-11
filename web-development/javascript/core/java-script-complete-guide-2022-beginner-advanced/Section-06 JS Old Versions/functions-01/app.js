const startGameBtn = document.getElementById('start-game-btn');

const start = function startGame() {
    console.log('Game is starting...');
};

const person = {
    name: 'Max',
    greet: function greet() {
        console.log('Hello there!');
    }
};

person.greet();


const ROCK = 'ROCK';
const PAPER = 'PAPER';
const SCISSORS = 'SCISSORS';
const RESULT_DRAW = 'DRAW';
const RESULT_PLAYER_WINS = 'PLAYER_WINS';
const RESULT_COMPUTER_WINS = 'PLAYER_WINS';


const DEFAULT_USER_CHOICE = ROCK;

let gameIsRunning = false;

const getPlayerChoice = () => {
    const selection = prompt(`${ROCK}, ${PAPER} or ${SCISSORS}?`, '').toUpperCase();

    if (selection != ROCK &&
        selection != PAPER && 
        selection != SCISSORS) {
        alert(`Invalid choice! We chose ${DEFAULT_USER_CHOICE} for you!`);
        return;
    }

    return selection;
}


const getComputerChoice = () => {
    const randomValue = Math.random();

    if (randomValue < 0.34) {
        return ROCK;
    } else if (randomValue < 0.67) {
        return PAPER;
    } else {
        return SCISSORS;
    }
}
const getWinner = (
    cChoice, 
    pChoice = cChoice === ROCK ? PAPER : DEFAULT_USER_CHOICE) => {

    if (cChoice === pChoice) {
        return RESULT_DRAW;
    } else if (
        cChoice == ROCK && pChoice === PAPER || 
        cChoice == PAPER && pChoice === SCISSORS ||
        cChoice == SCISSORS && pChoice === ROCK) {
        return RESULT_PLAYER_WINS;
    } else {
        return RESULT_COMPUTER_WINS;
    }
}

const getWinnerNew = (
    cChoice, 
    pChoice = DEFAULT_USER_CHOICE
    ) =>
   cChoice === pChoice 
    ? RESULT_DRAW 
    : (cChoice == ROCK && pChoice === PAPER) || 
      (cChoice == PAPER && pChoice === SCISSORS) ||
      (cChoice == SCISSORS && pChoice === ROCK)
       ? RESULT_PLAYER_WINS
       : RESULT_COMPUTER_WINS;
   

startGameBtn.addEventListener('click', () => {

    if (gameIsRunning) {
        return;
    }

    gameIsRunning = true;

    console.log('Game is starting...');
    const playerChoice = getPlayerChoice();
    const computerChoice = getComputerChoice();

    let winner;

    if (winner) {
        winner = getWinnerNew(computerChoice, playerChoice);
    } else {
        winner = getWinnerNew(computerChoice);
    }

    let message = `You picket ${playerChoice || DEFAULT_USER_CHOICE} and computer picket ${computerChoice}, therefore you `;

    if (winner == RESULT_DRAW) {
        message = message + 'had a draw.';
    } else if (winner == RESULT_PLAYER_WINS) {
        message = message + 'won.';
    } else {
        message = message + 'lost.';
    }

    alert(message);
    gameIsRunning = false;
});

// not related to game

const combine = (resultHandler, operation, ...numbers) =>  {
    
    const validateNumber = (number) => isNaN(number) ? 0 : number;

    let number = 0;

    for (const num of numbers) {

        if (operation == 'SUM') {
            number += validateNumber(num);           
        } else if (operation == 'SUBTRACT') {
            number -= validateNumber(num);
        }

    }

    return resultHandler(number);
}

// const subtractUp = function(resultHandler) {
//     let sub = 0;
//     for (const num of arguments) { // don't use that
//         sub -= num;
//     }
//     return resultHandler(sub);
// }


const showResult = (messageText, result) => {
    alert(messageText + ' ' + result)
}


combine(showResult.bind(this, 'The result after adding all numbers is: '), 'SUM',  1, 5, 'fdsa', -3, 6, 10);

combine(showResult.bind(this, 'The result after subtracting all numbers is: '), 'SUBTRACT', 1, 10, 15, 20);

