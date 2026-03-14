export const partialize = (fn, ...args) => fn.bind(null, ...args);

export const compose =
  (...fns) =>
  (value) =>
    fns.reduceRight((previousValue, fn) => fn(previousValue), value);

export const pipe =
  (...fns) =>
  (value) =>
    fns.reduce((previousValue, fn) => fn(previousValue), value);

// const sumItems = compose(sumItemsValue, filterItems, getItemsFromNotas);
// sumItems(notas)

export const takeUntil = (times, fn) => () => times-- > 0 && fn();

export const debounceTime = (milleseconds, fn) => {
  let timer = 0;
  return () => {
    clearTimeout(timer);
    timer = setTimeout(fn, milleseconds);
  };
};
