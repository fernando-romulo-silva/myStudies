function applicarDesconto(livros) {
  const desconto = 0.3;

  const livrosComDesconto = livros.map((livro) => {
    return { ...livro, preco: livro.preco - livro.preco * desconto };
  });

  return livrosComDesconto;
}
