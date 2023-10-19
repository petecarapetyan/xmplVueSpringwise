export interface IScoreType {
  id?: number;
  name?: string;
}

export class ScoreType implements IScoreType {
  constructor(public id?: number, public name?: string) {}
}
