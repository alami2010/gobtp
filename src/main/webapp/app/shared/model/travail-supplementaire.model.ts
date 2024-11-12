import dayjs from 'dayjs';
import { IChantier } from 'app/shared/model/chantier.model';

export interface ITravailSupplementaire {
  id?: string;
  name?: string;
  label?: string | null;
  date?: dayjs.Dayjs;
  chantier?: IChantier | null;
}

export const defaultValue: Readonly<ITravailSupplementaire> = {};
