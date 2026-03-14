async function buscarEndereco(cep) {
  let mensagemErro = document.getElementById("erro");
  mensagemErro.innerHTML = "";
  try {
    const consultaCep = await fetch(`https://viacep.com.br/ws/${cep}/json/`);
    const resposta = await consultaCep.json();

    if (resposta.erro) {
      throw new Error("CEP não encontrado");
    }

    const cidade = document.getElementById("cidade");
    cidade.value = resposta.localidade;

    const logradouro = document.getElementById("endereco");
    logradouro.value = resposta.logradouro;

    const estado = document.getElementById("estado");
    estado.value = resposta.uf;

    return resposta;
  } catch (erro) {
    mensagemErro.innerHTML = `<p>CEP não encontrado</p>`;
    console.log(erro);
  }
}

let cep = document.getElementById("cep");
cep.addEventListener("focusout", () => buscarEndereco(cep.value));
