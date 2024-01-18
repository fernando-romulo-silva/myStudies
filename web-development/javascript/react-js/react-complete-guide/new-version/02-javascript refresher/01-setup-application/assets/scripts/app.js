const list = document.querySelector('ul');
// list.remove();

// ---------------------------------------

function handleTimeout() {
  console.log('Timed out!');
}

const handleTimeout2 = () => {
  console.log('Timed out!');
};

setTimeout(handleTimeout, 2000);

// ---------------------------------------

function greeter(greetFn) {
  greetFn();
}

greeter(() => console.log('Hi'));

// ---------------------------------------

function init() {
  function greetDawn() {
    console.log('Hi Dawn');
  }

  greetDawn();
}

// ---------------------------------------
