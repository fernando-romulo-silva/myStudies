import Expenses from "./components/Expenses/Expenses";

const App = () => {

  const expenses = [
    { id: 'e1', title: 'Toilet Paper', amount: 94.12, date: new Date(2021, 2, 28) },
    { id: 'e2', title: 'New TV', amount: 799.99, date: new Date(2021, 2, 28) },
    { id: 'e3', title: 'Car Insurance', amount: 295.67, date: new Date(2021, 2, 28) },
    { id: 'e4', title: 'New Desk (Wooden)', amount: 450.0, date: new Date(2021, 2, 28) },
  ];

  return (
    <div>
      <h2>Let's get started!</h2>
      <Expenses expenses={expenses} />
    </div>
  );
}

export default App;
