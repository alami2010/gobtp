import dayjs from 'dayjs';
import { IChantier } from 'app/shared/model/chantier.model';

export interface IPhotoTravail {
  id?: string;
  description?: string | null;
  date?: dayjs.Dayjs;
  photoContentType?: string;
  photo?: string;
  chantier?: IChantier | null;
}

export const defaultValue: Readonly<IPhotoTravail> = {};
