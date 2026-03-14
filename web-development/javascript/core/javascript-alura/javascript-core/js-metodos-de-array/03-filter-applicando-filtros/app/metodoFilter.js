const botoes = document.querySelectorAll(".btn");

botoes.forEach((botao) => botao.addEventListener("click", filtrarLivros));

function filtrarLivros() {
  const elementBtn = document.getElementById(this.id);
  const categoria = elementBtn.value;

  let livrosFiltrados = livros.filter((livro) => livro.categoria == categoria);

  exibirLivrosNaTela(livrosFiltrados);
}
