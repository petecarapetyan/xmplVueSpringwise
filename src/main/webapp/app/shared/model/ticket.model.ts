import { IStudent } from '@/shared/model/student.model';

export interface ITicket {
  id?: number;
  issue?: string;
  timeStamp?: Date | null;
  student?: IStudent | null;
}

export class Ticket implements ITicket {
  constructor(public id?: number, public issue?: string, public timeStamp?: Date | null, public student?: IStudent | null) {}
}
