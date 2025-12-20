import { Personagem } from "./Personagem.js";

export class Arqueiro extends Personagem {
  static tipo = "Arqueiro";
  destreza;
  static descricao = "Você tem o meu arco!";

  constructor(nome, destreza) {
    super(nome);
    this.destreza = destreza;
  }

  obterInsignia() {
    if (this.destreza >= 5) return `Dominador de flechas`;

    return super.obterInsignia();
  }
}
