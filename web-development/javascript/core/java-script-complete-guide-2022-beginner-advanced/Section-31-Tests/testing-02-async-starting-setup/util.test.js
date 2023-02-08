// jest.mock('./http');

const { printTitle, loadTitle } = require('./util');

test('should print an uppercase text', () => {
    // expect(printTitle()).toBe('DELECTUS AUT AUTEM');
    loadTitle().then(title => {
        expect(title).toBe('DELECTUS AUT AUTEM');
    });
});