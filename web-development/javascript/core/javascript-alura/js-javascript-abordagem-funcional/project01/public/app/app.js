import { log, timeoutPromise, retry } from "./utils/promisse-helper.js";
import "./utils/array-helper.js";
import { notasService as service } from "./nota/service.js";
import { takeUntil, debounceTime, partialize, pipe } from "./utils/operator.js";
import { EventEmitter } from "./utils/event-emitter.js";
import { Maybe } from "./utils/maybe.js";

const maybe1 = Maybe.of(10);

const operations = pipe(
  partialize(takeUntil, 3),
  partialize(debounceTime, 500)
);

const action = operations(() =>
  retry(3, 3000, () => timeoutPromise(200, service.sumItems("2143")))
    .then((total) => EventEmitter.emit("itensTotalizados", total))
    .catch(console.log)
);

//const action = debounceTime(
//  500,
//  takeUntil(3, () =>
//    service.sumItems("2143").then(console.log).catch(console.log)
//  )
//);

document.querySelector("#myButton").onclick = action;
