import { Cliente } from "./Cliente.js";
import { ContaCorrente } from "./ContaCorrente.js";

const cliente1 = new Cliente("Ricardo", 11122233309);

const cliente2 = new Cliente();
cliente2.nome = "Alice";
cliente2._cpf = 88822233309;

const contaCorrenteRicardo = new ContaCorrente(cliente1, 1001);
// contaCorrenteRicardo.#saldo = 0; // Property "#saldo" is not acessbile outside class
contaCorrenteRicardo.depositar(500);

const conta2 = new ContaCorrente(cliente2, 102);
// conta2.cliente = cliente2;
// conta2.cliente = 0;
// conta2.cliente = new Cliente();
// conta2.cliente.nome = "Alice";
// conta2.cliente.cpf = "88822233309";
// conta2.agencia = 102;

let valor = 200;
contaCorrenteRicardo.transferir(valor, conta2);

console.log("valor: ", valor);

console.log("-------------------------------");
console.log(contaCorrenteRicardo);
console.log(conta2);
console.log("-------------------------------");
console.log(cliente1);
console.log(cliente2);

console.log(ContaCorrente.numeroDeContas);
