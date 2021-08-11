function funName() {
    console.log('Hello there!');
}

funName();


function passExam(name, score) {
    const passUni = 71;
    const passColl = 51;

    if (score >= passUni) {
        console.log(name + ' enrolled at the University with ' + score + ' points');
    } else if (score >= passColl) {
        console.log(name + ' enrolled at the College with ' + score + ' points');
    } else {
        console.log(name + ' failed');
    }
}

passExam('Bob', calcScore(45, 32));
passExam('Ann', 66);
passExam('Nick', 45);

function calcScore(quizScore, assayScore) {
    const score = quizScore + assayScore;

    return score;
}