import dayjs from 'dayjs';
import { IChantier } from 'app/shared/model/chantier.model';

export interface IMateriau {
  id?: string;
  name?: string;
  date?: dayjs.Dayjs;
  chantier?: IChantier | null;
}

export const defaultValue: Readonly<IMateriau> = {};
