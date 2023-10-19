export interface IFrog {
  id?: number;
  name?: string | null;
  age?: number | null;
  species?: string | null;
}

export class Frog implements IFrog {
  constructor(public id?: number, public name?: string | null, public age?: number | null, public species?: string | null) {}
}
