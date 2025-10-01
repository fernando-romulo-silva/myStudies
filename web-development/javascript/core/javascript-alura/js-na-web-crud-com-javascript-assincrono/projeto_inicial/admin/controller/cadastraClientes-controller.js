import { clienteService } from "../service/cliente-service.js";

const formulario = document.querySelector("[data-form]");

formulario.addEventListener("submit", (evento) => {
  evento.preventDefault();

  const nome = evento.target.querySelector("[data-nome]").value;
  const email = evento.target.querySelector("[data-email]").value;

  try {
    clienteService
      .criaCliente(nome, email)
      .then(() => (window.location.href = "../telas/cadastro_concluido.html"));
  } catch (error) {
    console.log(error);
    window.location.href = "../telas/erro.html";
  }
});
