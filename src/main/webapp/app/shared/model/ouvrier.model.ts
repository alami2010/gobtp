import { IUser } from 'app/shared/model/user.model';
import { TypeOuvrier } from 'app/shared/model/enumerations/type-ouvrier.model';

export interface IOuvrier {
  id?: string;
  name?: string;
  type?: keyof typeof TypeOuvrier;
  internalUser?: IUser | null;
}

export const defaultValue: Readonly<IOuvrier> = {};
