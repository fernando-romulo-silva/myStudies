export class Negociacao {
  constructor(
    private _data: Date,
    public readonly quantidade: number,
    public readonly valor: number
  ) {}

  data(): Date {
    return new Date(this._data.getTime());
  }

  get volume(): number {
    return this.quantidade * this.valor;
  }
}
