import { clienteService } from "../service/cliente-service.js";

const criaNovaLinha = ({ id, nome, email }) => {
  const linhaNovoCliente = document.createElement("tr");
  const conteudo = `
      <td class="td" data-td>${nome}</td>
      <td>${email}</td>
      <td>
          <ul class="tabela__botoes-controle">
              <li><a href="../telas/edita_cliente.html?id=${id}" class="botao-simples botao-simples--editar">Editar</a></li>
              <li><button class="botao-simples botao-simples--excluir" type="button">Excluir</button></li>
          </ul>
      </td>`;

  linhaNovoCliente.innerHTML = conteudo;
  linhaNovoCliente.dataset.id = id;

  return linhaNovoCliente;
};

const tabela = document.querySelector("[data-tabela]");

tabela.addEventListener("click", async (evento) => {
  let ehBotaoDeletar =
    evento.target.className == "botao-simples botao-simples--excluir";

  if (ehBotaoDeletar) {
    try {
      const linhaCliente = evento.target.closest("[data-id]");
      const id = linhaCliente.dataset.id;

      await clienteService.removeClient(id);
      linhaCliente.remove();
    } catch (error) {
      console.log(error);
      window.location.href = "../telas/erro.html";
    }
  }
});

const render = async () => {
  try {
    const clientes = await clienteService.listaClientes();
    clientes.forEach((elemento) => {
      tabela.appendChild(criaNovaLinha(elemento));
    });
  } catch (error) {
    console.log(error);
    window.location.href = "../telas/erro.html";
  }
};

render();
