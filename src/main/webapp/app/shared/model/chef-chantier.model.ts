import { IUser } from 'app/shared/model/user.model';

export interface IChefChantier {
  id?: string;
  name?: string;
  email?: string | null;
  phone?: string | null;
  internalUser?: IUser | null;
}

export const defaultValue: Readonly<IChefChantier> = {};
