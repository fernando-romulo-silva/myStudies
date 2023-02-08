const button = document.querySelector('button');
const output = document.querySelector('p');

function trackUserHandlerAncientOld() {
  navigator.geolocation.getCurrentPosition(
    posData => {
      setTimeout(() => {
        console.log(posData);
      }, 2000);
    }, 
    error => {
      console.log(error);
    }
  );

  setTimeout(() => {
    console.log('Timer done!');
  }, 0);

  console.log('Getting position ...');
}

// ----------------------------------------------------------------------------------

const setTimer = (duration) => {

  const promise = new Promise((resolve, reject) => {
    
    setTimeout(() => {
      resolve('Done!');
    }, duration);

  });

  return promise;
};

// ----------------------------------------------------------------------------------
function trackUserHandlerVeryOld() {
  navigator.geolocation.getCurrentPosition(
    posData => {
      setTimer(2000).then(data => {
        console.log(data, posData);
      });
    }, 
    error => {
      console.log(error);
    }
  );

  setTimer(1000).then(() => {
    console.log('Timer done!');
  });

  console.log('Getting position ...');
}


// ----------------------------------------------------------------------------------

const getPosition = (opts) => {

  const promise = new Promise((resolve, reject) => {
    
    navigator.geolocation.getCurrentPosition(
      
      success => {
        resolve(success);
      },

      error => {
        reject(error);
      }, 
      
      opts
    );
  });

  return promise;
};


function trackUserHandlerOld() {
  let positionData;

  getPosition()
    .then(posData => {
       positionData = posData;
       return setTimer(2000);
     })
    .catch(err => { // priors are skipted, but next don't
       console.log(err);
       return 'on we go ...'
    })    
    .then(data => {
       console.log(data, positionData);
    });

  setTimer(1000).then(() => {
    console.log('Timer done!');
  });

  console.log('Getting position ...');
}

// ----------------------------------------------------------------------------------

async function trackUserHandler() {
  // let positionData;

  let posData;
  let timerData;

  try {
  
    posData = await getPosition();
    timerData = await setTimer(2000);
    
  } catch (error) {
    console.log(error);
  }

  console.log(timerData, posData);

  setTimer(1000).then(() => {
    console.log('Timer done!');
  });

  console.log('Getting position ...');
}


// ----------------------------------------------------------------------------------

async function trackUserHandlerFinal() {
  // let positionData;

  let posData;
  let timerData;

  try {
  
    posData = await getPosition();
    timerData = await setTimer(2000);
    
  } catch (error) {
    console.log(error);
  }

  console.log(timerData, posData);

  setTimer(1000).then(() => {
    console.log('Timer done!');
  });

  console.log('Getting position ...');
}

button.addEventListener('click', trackUserHandlerFinal);

// Promise.race([
//   getPosition(),
//   setTimer(1000)
// ]).then(data => console.log(data));


// Promise.all([
//   getPosition(),
//   setTimer(1000)
// ]).then(data => console.log(data));


Promise.allSettled([
  getPosition(),
  setTimer(1000)
]).then(data => console.log(data));

// let result = 0;

// for (let i = 0; i < 100000000; i++) {
//   result += i;
// }

// console.log(result);