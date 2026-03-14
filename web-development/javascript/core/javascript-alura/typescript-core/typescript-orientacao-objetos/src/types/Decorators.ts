export function ValidaDebito(
  target: any,
  property: string,
  descriptor: PropertyDescriptor,
) {
  const originalMethod = descriptor.value;

  descriptor.value = function (valorDoDebito: number) {
    if (valorDoDebito <= 0) {
      throw new Error("O valor a ser debitado precisa sser maior do que zero");
    }

    if (valorDoDebito > this.saldo) {
      throw new Error("Seu saldo é insuficiente para realizar a operacao");
    }

    return originalMethod.apply(this, [valorDoDebito]);
  };

  return descriptor;
}

export function ValidaDeposito(
  target: any,
  property: string,
  descriptor: PropertyDescriptor,
) {
  const originalMethod = descriptor.value;

  descriptor.value = function (valorDoDeposito: number) {
    if (valorDoDeposito <= 0) {
      throw new Error("O valor a ser depositado deve ser maior que zero!");
    }

    return originalMethod.apply(this, [valorDoDeposito]);
  };

  return descriptor;
}
