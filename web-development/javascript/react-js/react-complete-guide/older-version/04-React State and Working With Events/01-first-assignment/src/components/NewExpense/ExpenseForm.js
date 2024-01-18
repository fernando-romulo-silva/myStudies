
import React, { useState } from "react";

import './ExpenseForm.css';

const ExpenseForm = (props) => {

    const [enteredTitle, setEnteredTitle] = useState('');
    const [enteredAmount, setEnteredAmount] = useState('');
    const [enteredDate, setEnteredDate] = useState('');
    
    // const [userInput, setUserInput] = useState({
    //     enteredTitle: '',
    //     enteredAmount: '',
    //     enteredDate: ''
    // });

    const titleChageHandler = (event) => {                
        setEnteredTitle(event.target.value);
        
        // setUserInput({
        //     ...userInput,
        //     enteredTitle: event.target.value
        // });

        // setUserInput((prevState) => {
        //     return { ...prevState, enteredTitle: event.target.value };
        // });
    };

    const amountChageHandler = (event) => {                
        setEnteredAmount(event.target.value);

        // setUserInput({
        //     ...userInput,
        //     enteredAmount: event.target.value
        // });        

        // setUserInput((prevState) => {
        //     return { ...prevState, enteredAmount: event.target.value };
        // });        
    };

    const dateChageHandler = (event) => {                
        setEnteredDate(event.target.value);

        // setUserInput({
        //     ...userInput,
        //     enteredDate: event.target.value
        // });     
        
        // setUserInput((prevState) => {
        //     return { ...prevState, enteredDate: event.target.value };
        // });                
    };

    const submitHandler = (event) => {
        event.preventDefault();

        const expenseData = {
            title: enteredTitle,
            amount: +enteredAmount,
            date: new Date(enteredDate)
        };

        props.onSaveExpenseData(expenseData);

        setEnteredTitle('');
        setEnteredAmount('');
        setEnteredDate('');
    };

    return <form onSubmit={submitHandler}>
        <div className="new-expense__controls">
            <div className="new-expense__control">
                <label>Title</label>
                <input type='text' value={enteredTitle} onChange={titleChageHandler} />
            </div>
            <div className="new-expense__control">
                <label>Amount</label>
                <input value={enteredAmount} type='number' min="0.01" step="0.01" onChange={amountChageHandler} />
            </div>            
            <div className="new-expense__control">
                <label>Date</label>
                <input value={enteredDate} type='date' min="2019-01-01" max="2022-12-31" onChange={dateChageHandler} />
            </div>
            <div className="new-expense__actions">
                <button type='submit'>Add Expense</button>
            </div>            
        </div>
    </form>
};

export default ExpenseForm;