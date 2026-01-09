export class SistemaAutenticacao {
  static login(autenticavel, senha) {
    if (SistemaAutenticacao.ehAuthenticavel(autenticavel)) {
      return autenticavel.autenticar(senha);
    }

    return false;
  }

  // Ducktype
  static ehAuthenticavel(autenticavel) {
    return (
      "autenticar" in autenticavel &&
      autenticavel.autenticar instanceof Function
    );
  }
}
