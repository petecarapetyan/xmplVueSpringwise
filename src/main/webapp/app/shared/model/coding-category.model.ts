export interface ICodingCategory {
  id?: number;
  name?: string | null;
}

export class CodingCategory implements ICodingCategory {
  constructor(public id?: number, public name?: string | null) {}
}
