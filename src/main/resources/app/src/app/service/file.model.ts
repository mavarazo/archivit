import { Tag } from './tag.model';

export class File {
    id: number;
    name: String;
    extension: String;
    path: String;
    creationTime: Date;
    lastAccessTime: Date;
    lastModifiedTime: Date;
    size: number;
    isStarred: boolean;
    tags?: Tag[];
}
