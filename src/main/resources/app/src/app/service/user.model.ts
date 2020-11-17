import { Tag } from './tag.model';

export class User {
  id: number;
  name: String;
  totalFiles: number;
  totalUntaggedFiles: number;
  totalStarredFiles: number;
  totalTags: number;
  extensions?: String[];
  topTags: Map<number, Tag>;
}
