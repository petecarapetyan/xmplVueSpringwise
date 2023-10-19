export interface ISpringProject {
  id?: number;
  title?: string | null;
  description?: string | null;
  imagePath?: string | null;
  url?: string | null;
}

export class SpringProject implements ISpringProject {
  constructor(
    public id?: number,
    public title?: string | null,
    public description?: string | null,
    public imagePath?: string | null,
    public url?: string | null
  ) {}
}
