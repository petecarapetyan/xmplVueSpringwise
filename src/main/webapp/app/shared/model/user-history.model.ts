export interface IUserHistory {
  id?: number;
  name?: string | null;
  issue?: string | null;
  issueDate?: Date | null;
}

export class UserHistory implements IUserHistory {
  constructor(public id?: number, public name?: string | null, public issue?: string | null, public issueDate?: Date | null) {}
}
