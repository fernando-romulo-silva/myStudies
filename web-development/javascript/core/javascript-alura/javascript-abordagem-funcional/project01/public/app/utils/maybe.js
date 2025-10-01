export class Maybe {
  constructor(value) {
    this._value = value;
  }

  static of(value) {
    return new Maybe(value);
  }

  isNonthing() {
    return this._value === null || this._value === undefined;
  }

  map(fn) {
    if (this.isNonthing()) {
      return Maybe.of(null);
    }
    return Maybe.of(fn(this._value));
  }

  getOrElse(value) {
    if (this.isNonthing()) {
      return value;
    }

    return this._value;
  }
}
