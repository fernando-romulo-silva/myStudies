const botoes = document.querySelectorAll(".btn");

botoes.forEach((botao) => botao.addEventListener("click", filtrarLivros));

function filtrarLivros() {
  const elementBtn = document.getElementById(this.id);
  const categoria = elementBtn.value;

  let livrosFiltrados =
    categoria == "disponivel"
      ? filtrarPorDisponibilidade()
      : filtrarPorCategoria(categoria);

  exibirLivrosNaTela(livrosFiltrados);

  if (categoria === "disponivel") {
    const valorTotal = calcularValorTotalDeLivrosDisponiveis(livrosFiltrados);
    exibirValorTotalDisponiveisNaTela(valorTotal);
  }
}

function filtrarPorCategoria(categoria) {
  return livros.filter((livro) => livro.categoria == categoria);
}

function filtrarPorDisponibilidade() {
  return livros.filter((livro) => livro.quantidade > 0);
}

function exibirValorTotalDisponiveisNaTela(valorTotal) {
  elementoComValorTotalDeLivrosDisponiveis.innerHTML = `
    <div class="livros__disponiveis">
      <p>Todos os livros disponíveis por R$ <span id="valor">${valorTotal.toFixed(
        2
      )}</span></p>
    </div>
  `;
}
