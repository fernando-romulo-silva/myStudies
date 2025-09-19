import { handleStatus } from "../utils/promisse-helper.js";
import { partialize, pipe } from "../utils/operator.js";

const API = "http://localhost:3000/notas";

const getItemsFromNotas = (notas) => notas.$flatMap((notas) => notas.itens);

const filterItemsByCode = (code, items) =>
  items.filter((item) => item.codigo == code);

const sumItemsValue = (items) =>
  items.reduce((total, item) => total + item.valor, 0);

const ehDivisivel = (divisor, numero) => !(numero % divisor);
const ehDivisivelPorDois = ehDivisivel.bind(null, 2);

console.log(ehDivisivel(2, 10));
console.log(ehDivisivelPorDois(10));

/*
const sumItems01 = (code) => (notas) =>
  notas
    .$flatMap((nota) => nota.itens)
    .filter((item) => item.codigo == code)
    .reduce((total, item) => total + item.valor, 0);
*/

export const notasService = {
  listaAll() {
    return fetch(API)
      .then(handleStatus)
      .catch((err) => {
        console.log(err);
        return Promise.reject("Não foi possivel obter as notas fiscais");
      });
  },

  sumItems(code) {
    //const filterItems = filterItemsByCode.bind(null, code);
    const filterItems = partialize(filterItemsByCode, code);
    const sumItems = pipe(getItemsFromNotas, filterItems, sumItemsValue);

    return (
      this.listaAll()
        // .then(sumItems01(code))
        // .then((notas) => sumItemsValue(filterItems(getItemsFromNotas(notas))))
        .then(sumItems)
    );
  },
};
