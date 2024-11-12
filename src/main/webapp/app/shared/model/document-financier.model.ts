import { IChantier } from 'app/shared/model/chantier.model';

export interface IDocumentFinancier {
  id?: string;
  nom?: string;
  fichierContentType?: string;
  fichier?: string;
  chantier?: IChantier | null;
}

export const defaultValue: Readonly<IDocumentFinancier> = {};
