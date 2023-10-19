import { ITicket } from '@/shared/model/ticket.model';

export interface IStudent {
  id?: number;
  name?: string;
  initials?: string;
  tickets?: ITicket[] | null;
}

export class Student implements IStudent {
  constructor(public id?: number, public name?: string, public initials?: string, public tickets?: ITicket[] | null) {}
}
