function gerarDiaDaSemana() {
  const dataAtual = new Date();

  const diaDaSemana = dataAtual.toLocaleDateString("pt-BR", {
    weekday: "long",
  });

  const data = dataAtual.toLocaleDateString("pt-BR");
  const hora = dataAtual.toLocaleTimeString("pt-BR", {
    hour: "numeric",
    minute: "numeric",
  });

  return `${diaDaSemana} (${data}) às ${hora}`;
}

export default gerarDiaDaSemana;
