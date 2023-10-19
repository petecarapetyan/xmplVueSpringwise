export interface ITruck {
  id?: number;
  modelName?: string | null;
  make?: string | null;
  motorSize?: number | null;
  color?: string | null;
}

export class Truck implements ITruck {
  constructor(
    public id?: number,
    public modelName?: string | null,
    public make?: string | null,
    public motorSize?: number | null,
    public color?: string | null
  ) {}
}
