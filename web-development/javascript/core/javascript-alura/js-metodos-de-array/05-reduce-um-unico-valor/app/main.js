let livros = [];

const endPointDaAPI =
  "https://guilhermeonrails.github.io/casadocodigo/livros.json";

getBuscarLivrosDaAPI();

async function getBuscarLivrosDaAPI() {
  const res = await fetch(endPointDaAPI);

  livros = await res.json();

  let livrosComDesconto = applicarDesconto(livros);

  exibirLivrosNaTela(livrosComDesconto);
}
