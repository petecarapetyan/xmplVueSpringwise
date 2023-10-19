export interface IScore {
  id?: number;
  points?: string;
}

export class Score implements IScore {
  constructor(public id?: number, public points?: string) {}
}
