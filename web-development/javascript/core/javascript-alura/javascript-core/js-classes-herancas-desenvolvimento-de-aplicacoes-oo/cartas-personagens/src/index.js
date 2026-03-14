import { Personagem } from "./modules/Personagem.js";
import { PersonagemView } from "./components/personagem-view.js";
import { Mago } from "./modules/Mago.js";
import { Arqueiro } from "./modules/Arqueiro.js";
import { ArqueiroMago } from "./modules/ArqueiroMago.js";
import { Guerreiro } from "./modules/Guerreiro.js";

const magoAntonio = new Mago("Antonio", 4, "fogo", 4, 3);
const magaJulia = new Mago("Julia", 8, "gelo", 7, 10);
const arqueiroBruno = new Arqueiro("Bruno", 7, 8);

const arqueiroMagoChico = new ArqueiroMago("Chico", 7, 10, "ar", 4, 8);

const guerreiraJorge = new Guerreiro("Jorge", 8);
const personagens = [
  magoAntonio,
  magaJulia,
  arqueiroBruno,
  arqueiroMagoChico,
  guerreiraJorge,
];

new PersonagemView(personagens).render();

console.log(Personagem.verificarVencedor(arqueiroBruno, magoAntonio));

// console.log(magaJulia(arqueiroBruno, magoAntonio));
