import { IChantier } from 'app/shared/model/chantier.model';

export interface IPlan {
  id?: string;
  name?: string;
  file?: string;
  chantier?: IChantier | null;
}

export const defaultValue: Readonly<IPlan> = {};