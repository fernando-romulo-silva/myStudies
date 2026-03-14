export function imprimir(...objetos: Array<{ paraTexto(): string }>): void {
  for (let objeto of objetos) {
    console.log(objeto.paraTexto());
  }
}
