export interface IAirplane {
  id?: number;
  model?: string | null;
  make?: string | null;
  color?: string | null;
}

export class Airplane implements IAirplane {
  constructor(public id?: number, public model?: string | null, public make?: string | null, public color?: string | null) {}
}
