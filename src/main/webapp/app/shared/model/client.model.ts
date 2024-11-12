import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IClient {
  id?: string;
  name?: string;
  isProfessional?: boolean;
  date?: dayjs.Dayjs;
  adresse?: string | null;
  info?: string | null;
  internalUser?: IUser | null;
}

export const defaultValue: Readonly<IClient> = {
  isProfessional: false,
};
