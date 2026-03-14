let btnOrdernarPorPreco = document.getElementById("btnOrdenarPorPreco");

btnOrdernarPorPreco.addEventListener("click", ordernarLivrosPorPreco);

function ordernarLivrosPorPreco() {
  let livrosOrdenados = livros.sort((a, b) => a.preco - b.preco);
  exibirLivrosNaTela(livrosOrdenados);
}
