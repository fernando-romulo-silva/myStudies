// DOM Elements
const mainPage = document.querySelector('.main-page');

const loginPage = document.querySelector('.login-page');

const middleContent = document.querySelector('.middle-content');

const newsFeedPage = document.querySelector('.feeds-page');

const loginModel = document.querySelector('.login-model');

const modalX = document.querySelector('.login-model i');

const loginFormBtn = document.querySelector('.login-form-btn');

/*******************************************************************/

const btnTop = document.querySelector('.btn-top');


/*******************************************************************/

// Main Page
const goToLoginpage = () => {
	mainPage.style.display = 'none';
	loginPage.style.display = 'grid';
};

middleContent.addEventListener('click', (e) =>{

	if (e.target.classList[1] === 'main-btn') {
		goToLoginpage();
	}
	
	console.log(e.target.classList[1]);
});

btnTop.addEventListener('click', () => {
	const inputUserInfo = document.querySelector('.user-info');

	const inputPassword = document.querySelector('.password');
	
	if (inputUserInfo.value !== "" && inputUserInfo.value !== "") {
		mainPage.style.display = 'none';		
		newsFeedPage.style.display = 'block';
	} else {
		goToLoginpage();
		loginModel.style.display = 'block';
	}
});

// Login page

modalX.addEventListener('click', () => {

	loginModel.style.display = 'none';

});

loginFormBtn.addEventListener('click', () => {

	const loginUserInfo = document.querySelector('.login-user-info');
	const loginPassword = document.querySelector('.login-password');

	if (loginUserInfo.value !== "" && loginPassword.value !== "") {
		
		loginPage.style.display = 'none';
		newsFeedPage.style.display = 'block';

	} else {
		
		loginModel.style.display = 'block';
	}

});

