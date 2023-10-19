export interface IMovie {
  id?: number;
  name?: string | null;
  yearOf?: number | null;
  genre?: string | null;
  rating?: string | null;
}

export class Movie implements IMovie {
  constructor(
    public id?: number,
    public name?: string | null,
    public yearOf?: number | null,
    public genre?: string | null,
    public rating?: string | null
  ) {}
}
