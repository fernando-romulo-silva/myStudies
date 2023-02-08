
const intervalId = setInterval(() => {
    console.log('Sending analytics ...');
}, 2000);

document.getElementById('stop-analytics-btn').addEventListener('click', () => {
    clearInterval(intervalId);
});