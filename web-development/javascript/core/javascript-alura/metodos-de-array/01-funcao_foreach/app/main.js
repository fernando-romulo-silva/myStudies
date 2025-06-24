let livros = [];

const endPointDaAPI =
  "https://guilhermeonrails.github.io/casadocodigo/livros.json";

const elementoParaInserirLivros = document.getElementById("livros");

getBuscarLivrosDaAPI();

exibirLivrosNaTela(livros);

async function getBuscarLivrosDaAPI() {
  const res = await fetch(endPointDaAPI);

  livros = await res.json();
  exibirLivrosNaTela(livros);
}

function exibirLivrosNaTela(listaDeLivros) {
  listaDeLivros.forEach((livro) => {
    elementoParaInserirLivros.innerHTML += `
    <div class="livro">
      <img class="livro__imagens" src="${livro.imagem}"
        alt="${livro.alt}" />
      <h2 class="livro__titulo">
        ${livro.titulo}
      </h2>
      <p class="livro__descricao">${livro.autor}</p>
      <p class="livro__preco" id="preco">R$${livro.preco}</p>
      <div class="tags">
        <span class="tag">${livro.categoria}</span>
      </div>
    </div>`;
  });
}
