import { Keyword } from './keyword';

export class Rule {
  id: number;
  name: string;
  targetPath: string;
  keywords: Keyword[];
}
