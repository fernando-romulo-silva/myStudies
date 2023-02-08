const puppeteer = require('puppeteer');
const { generateText, checkAndGenerate } = require('./util');

// unit
test('should output name and age', () => {
    const text = generateText('Max', 29); 
    expect(text).toBe('Max (29 years old)');
});

test('should output data-less teest', () => {
    const text = generateText('', null);
    expect(text).toBe(' (null years old)');
});

// integration
test('should generate a valid text output', () => {
    const text = checkAndGenerate('Max', 29);
    expect(text).toBe('Max (29 years old)');
});

// e2e
test('should create an element with text and correct class', async () => {
    const browser = await puppeteer.launch({
        headless: true,
        slowMo: 80,
        args: ['--window-size=1920,1080']
    });

    const page = await browser.newPage();
    await page.goto('file:///home/fernando/Development/workspaces/eclipse-workspace/myStudies/web-development/javascript/core/java-script-complete-guide-2022-beginner-advanced/Section-31-Tests/testing-01-starting-setup/index.html');

    await page.click('input#name');
    await page.type('input#name', 'Anna');

    await page.click('input#age');
    await page.type('input#age', '28');

    await page.click('#btnAddUser');

    const finalText = await page.$eval('.user-item', el => el.textContent);
    expect(finalText).toBe('Anna (28 years old)');
});