import React, { useState } from 'react';

import './Expenses.css';

import ExpensesFilter from './ExpensesFilter';
import Card from '../UI/Card';
import ExpensesList from './ExpensesList';

const Expenses = (props) => {
  const [filteredYear, setFilteredYear] = useState('2020');

  const filterChangeHandler = selectedYear => {
    setFilteredYear(selectedYear);
  };

  const filteredExpenses = props.expenses.filter(item => {
    return item.date.getFullYear().toString() === filteredYear;
  });



  return (

    <div >      
      <Card className="expenses">
        
        <ExpensesFilter selected={filteredYear} onChangeFilter={filterChangeHandler} />
        
        <ExpensesList items ={filteredExpenses} />

        { 
          // expensesContent 
        }
        
        
        {
          // filteredExpenses.length === 0 && <p>No expenses found.</p>
        }
        
        {
          // filteredExpenses.length !== 0 && 
          //   filteredExpenses.map((item, index) => (
          //     <ExpenseItem
          //       key={index}
          //       title={item.title}
          //       amount={item.amount}
          //       date={item.date}
          //     />
          //   ))
        }

      </Card>

    </div>
  );
}

export default Expenses;
