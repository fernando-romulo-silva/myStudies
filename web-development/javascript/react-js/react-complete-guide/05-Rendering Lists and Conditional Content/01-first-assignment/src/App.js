import React, {useState} from "react";
import Expenses from "./components/Expenses/Expenses";
import NewExpense from "./components/NewExpense/NewExpense";

const DAMMY_EXPENSES = [
  { id: 'e1', title: 'Toilet Paper', amount: 94.12, date: new Date(2021, 2, 28) },
  { id: 'e2', title: 'New TV', amount: 799.99, date: new Date(2021, 2, 28) },
  { id: 'e3', title: 'Car Insurance', amount: 295.67, date: new Date(2021, 5, 28) },
  { id: 'e4', title: 'New Desk (Wooden)', amount: 450.0, date: new Date(2020, 9, 28) },
];

const App = () => {

  const [expenses, setExpenses] = useState(DAMMY_EXPENSES);

  const addExpenseHandler = expense => {
    setExpenses(prevExpenses => {
      return [expense, ...prevExpenses];
    });
  };

  React.createElement(
    'div',
    {},
    React.createElement('h2', {}, "Let's get started1"),
    React.createElement(Expenses, {items: expenses}),
  );

  return (
    <div>
      <NewExpense onAddExpense={addExpenseHandler} />
      <Expenses expenses={expenses} />
    </div>
  );
}

export default App;
