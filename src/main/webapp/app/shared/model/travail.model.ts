import { IChantier } from 'app/shared/model/chantier.model';

export interface ITravail {
  id?: string;
  name?: string;
  label?: string | null;
  chantier?: IChantier | null;
}

export const defaultValue: Readonly<ITravail> = {};
