import { Transacao } from "../types/Transacao.js";
import { TipoTransacao } from "../types/TipoTransacao.js";
import SaldoComponent from "./saldo-component.js";
import Conta from "../types/Conta.js";
import ExtratoComoponente from "./extrato-component.js";

const elementoFormulario = document.querySelector(
  ".block-nova-transacao form",
) as HTMLFormElement;

elementoFormulario.addEventListener("submit", function (event) {
  event.preventDefault();

  try {
    if (!elementoFormulario.checkValidity()) {
      alert("Por favor, preencha todos os campos da transação!");
      return;
    }

    const inputTipoTransacao = elementoFormulario.querySelector(
      "#tipoTransacao",
    ) as HTMLSelectElement;

    const inputValor = elementoFormulario.querySelector(
      "#valor",
    ) as HTMLInputElement;

    const inputData = elementoFormulario.querySelector(
      "#data",
    ) as HTMLInputElement;

    const tipoTransacao: TipoTransacao =
      inputTipoTransacao.value as TipoTransacao;
    const valor: number = parseFloat(inputValor.value);
    const data: Date = new Date(inputData.value + " 00:00:00");

    const novaTransacao: Transacao = {
      tipoTransacao: tipoTransacao,
      valor: valor,
      data: data,
    };

    Conta.registrarTransacao(novaTransacao);
    SaldoComponent.atualizar();
    ExtratoComoponente.atualizar();
    elementoFormulario.reset();
  } catch (error: any) {
    alert(error.message);
  }
});
