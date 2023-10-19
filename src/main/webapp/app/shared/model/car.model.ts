export interface ICar {
  id?: number;
  motorSize?: string | null;
  modelName?: string | null;
  wheelSize?: string | null;
  transmission?: string | null;
  color?: string | null;
  yearOf?: number | null;
  price?: number | null;
}

export class Car implements ICar {
  constructor(
    public id?: number,
    public motorSize?: string | null,
    public modelName?: string | null,
    public wheelSize?: string | null,
    public transmission?: string | null,
    public color?: string | null,
    public yearOf?: number | null,
    public price?: number | null
  ) {}
}
