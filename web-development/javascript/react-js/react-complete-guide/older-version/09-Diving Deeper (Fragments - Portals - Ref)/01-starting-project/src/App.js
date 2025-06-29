import React, { useState } from 'react';

import AddUser from './components/Users/AddUser';
import UsersList from './components/Users/UsersList';

function App() {
  const [usersList, setUsersList] = useState([]);

  const onAddUserHandler = (uName, uAge) => {
    setUsersList((prevUsersList) => {
      return [
        ...prevUsersList,
        { name: uName, age: uAge, id: Math.random.toString() },
      ];
    });
  };

  return (
    <>
      <AddUser onAddUser={onAddUserHandler} />
      <UsersList users={usersList} />
    </>
  );
}

export default App;
