import dayjs from 'dayjs';
import { IOuvrier } from 'app/shared/model/ouvrier.model';
import { IChefChantier } from 'app/shared/model/chef-chantier.model';
import { IClient } from 'app/shared/model/client.model';

export interface IChantier {
  id?: string;
  name?: string;
  adresse?: string;
  desc?: string | null;
  status?: string;
  date?: dayjs.Dayjs;
  ouvriers?: IOuvrier[] | null;
  chefChantier?: IChefChantier | null;
  client?: IClient | null;
}

export const defaultValue: Readonly<IChantier> = {};
