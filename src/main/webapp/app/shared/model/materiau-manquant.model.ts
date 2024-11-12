import dayjs from 'dayjs';
import { IChantier } from 'app/shared/model/chantier.model';

export interface IMateriauManquant {
  id?: string;
  name?: string;
  quantity?: number;
  date?: dayjs.Dayjs | null;
  chantier?: IChantier | null;
}

export const defaultValue: Readonly<IMateriauManquant> = {};
