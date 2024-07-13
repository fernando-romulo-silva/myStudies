import { useState } from "react";

import Counter from "./components/Counter/Counter.jsx";
import Header from "./components/Header.jsx";
import { log } from "./log.js";
import ConfigureCounter from "./components/Counter/ConfigureCounter.jsx";
import CounterShared from "./components/Counter/CounterShared.jsx";

function App() {
  log("<App /> rendered");

  const [chosenCount, setChosenCount] = useState(0);

  function handleSetCount(newCount) {
    setChosenCount(newCount);
    setChosenCount((prevChosenCount) => prevChosenCount + 1);
  }

  return (
    <>
      <Header />
      <main>
        <ConfigureCounter onSet={handleSetCount} />

        {/*
          <Counter initialCount={chosenCount} />         
        */}

        <CounterShared key={chosenCount} initialCount={chosenCount} />
        <CounterShared initialCount={1} />
      </main>
    </>
  );
}

export default App;
