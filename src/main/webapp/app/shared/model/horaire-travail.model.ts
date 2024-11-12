import dayjs from 'dayjs';
import { IChantier } from 'app/shared/model/chantier.model';
import { IOuvrier } from 'app/shared/model/ouvrier.model';

export interface IHoraireTravail {
  id?: string;
  debutMatin?: dayjs.Dayjs | null;
  finMatin?: dayjs.Dayjs | null;
  debutSoir?: dayjs.Dayjs | null;
  finSoir?: dayjs.Dayjs | null;
  date?: dayjs.Dayjs;
  jour?: string;
  chantier?: IChantier | null;
  ouvrier?: IOuvrier | null;
}

export const defaultValue: Readonly<IHoraireTravail> = {};
