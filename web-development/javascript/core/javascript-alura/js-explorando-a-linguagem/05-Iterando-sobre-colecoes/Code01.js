console.log(`Trabalhando com condicionais`);
const listaDeDestinos = new Array(`Salvador`, `São Paulo`, `Rio de Janeiro`);

const idadeComprador = 18;
const estaAcompanhada = false;
let temPassagemComprada = false;
const destino = "São Paulo";

console.log("\n Destinos possíveis:");
console.log(listaDeDestinos);

const podeComprar = idadeComprador >= 18 || estaAcompanhada == true;

let contador = 0;
let destinoExist = false;

while (!destinoExist && contador < listaDeDestinos.length) {
  console.log(listaDeDestinos[contador]);

  if (listaDeDestinos[contador] === destino) {
    destinoExist = true;
  }

  contador += 1;
}

console.log("Destino existe:", destinoExist);
