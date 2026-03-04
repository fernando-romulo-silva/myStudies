console.log(`Trabalhando com condicionais`);
const listaDeDestinos = new Array(`Salvador`, `São Paulo`, `Rio de Janeiro`);

const idadeComprador = 18;
const estaAcompanhada = false;
let temPassagemComprada = false;
const destino = "São Paulo";

console.log("\n Destinos possíveis:");
console.log(listaDeDestinos);

const podeComprar = idadeComprador >= 18 || estaAcompanhada == true;

let destinoExist = false;

for (let i = 0; i < 3; i++) {
  if (listaDeDestinos[contador] == destino) {
    destinoExiste = true;
  }
}

if (podeComprar && destinoExiste) {
  console.log("Boa Viagem");
} else {
  console.log("Desculpe tivemos um erro!");
}
