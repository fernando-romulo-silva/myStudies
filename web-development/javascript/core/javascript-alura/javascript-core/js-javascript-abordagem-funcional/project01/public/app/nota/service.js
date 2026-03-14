import { handleStatus } from "../utils/promisse-helper.js";
import { partialize, pipe } from "../utils/operator.js";
import { Maybe } from "../utils/maybe.js";

const API = "http://localhost:3000/notas";

const getItemsFromNotas = (notasM) =>
  notasM.map((notas) => notas.$flatMap((nota) => nota.itens));

const filterItemsByCode = (code, itemsM) =>
  itemsM.map((items) => items.filter((item) => item.codigo == code));

const sumItemsValue = (itemsM) =>
  itemsM.map((items) => items.reduce((total, item) => total + item.valor, 0));

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
      .then((notas) => Maybe.of(notas))
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
        .then((result) => result.getOrElse(0))
    );
  },
};
