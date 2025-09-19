import { log } from "./utils/promisse-helper.js";
import "./utils/array-helper.js";
import { notasService as service } from "./nota/service.js";
import { takeUntil, debounceTime, partialize, pipe } from "./utils/operator.js";

const operations = pipe(
  partialize(takeUntil, 3),
  partialize(debounceTime, 500)
);

const action = operations(() =>
  service.sumItems("2143").then(console.log).catch(console.log)
);

//const action = debounceTime(
//  500,
//  takeUntil(3, () =>
//    service.sumItems("2143").then(console.log).catch(console.log)
//  )
//);

document.querySelector("#myButton").onclick = action;
