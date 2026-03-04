const consultaCep = fetch("https://viacep.com.br/ws/01001000/json/")
  .then((resposta) => resposta.json())
  .then((dados) => {
    if (dados.erro) {
      throw new Error("CEP não encontrado");
    }
    console.log(dados);
  })
  .catch((erro) => console.log(erro))
  .finally(() => console.log("Processamento concluído"));

console.log(consultaCep);
