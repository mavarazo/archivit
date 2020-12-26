import { Match } from "./match";

export class Audit {
  id: number;
  filePath: string;
  status: string;
  matches: Match[];
}