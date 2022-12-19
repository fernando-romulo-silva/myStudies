const customers = ['Max', 'Manual', 'Anna'];

const activeCustomers = ['Max', 'Manual'];

const inactiveCustomers = _.difference(customers, activeCustomers);

console.log(inactiveCustomers);