import './Expenses.css';
import ExpenseItem from './ExpenseItem';
import Card from '../UI/Card';

const Expenses = (props) => {
  return (
    <Card className="expenses">
      {props.expenses.map((item) => (
        <ExpenseItem
          className="expenses"
          title={item.title}
          amount={item.amount}
          date={item.date}
        />
      ))}
    </Card>
  );
}

export default Expenses;
