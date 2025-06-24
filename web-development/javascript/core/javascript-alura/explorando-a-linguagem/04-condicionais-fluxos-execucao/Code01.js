console.log(`Trabalhando com listas`);

const listaDeDestinos = new Array(`Salvador`, `São Paulo`, `Rio de Janeiro`);

listaDeDestinos.push(`Curitiba`);

listaDeDestinos.splice(1, 1);

console.log(listaDeDestinos);

const idadeComprador = 15;
const estaAcompanhada = true;

console.log("Destinos possíveis:");
console.log(listaDeDestinos);

if (idadeComprador >= 18 || estaAcompanhada == true) {
  console.log("Comprador maior de idade");
} else {
  console.log("Não é maior de idade e não posso vender");
}
