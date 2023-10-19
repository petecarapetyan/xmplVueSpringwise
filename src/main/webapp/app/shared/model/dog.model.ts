export interface IDog {
  id?: number;
  name?: string | null;
  age?: number | null;
  breed?: string | null;
}

export class Dog implements IDog {
  constructor(public id?: number, public name?: string | null, public age?: number | null, public breed?: string | null) {}
}
