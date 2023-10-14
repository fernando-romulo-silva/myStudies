import { configureStore } from '@reduxjs/toolkit';
import authReducer from './auth';
import counterReducer from './counter';

// const store = configureStore({
//   reducer: counterSlice.reducer,
// });

const store = configureStore({
  reducer: { counter: counterReducer, auth: authReducer },
});

export default store;
