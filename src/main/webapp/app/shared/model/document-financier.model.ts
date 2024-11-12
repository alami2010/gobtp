import { IChantier } from 'app/shared/model/chantier.model';

export interface IDocumentFinancier {
  id?: string;
  nom?: string;
  file?: string;
  chantier?: IChantier | null;
}

export const defaultValue: Readonly<IDocumentFinancier> = {};
