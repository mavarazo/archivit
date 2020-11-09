export class File {
    id: number;
    name: string;
    path: string;
    isRegularFile: boolean;
    creationTime: Date;
    lastAccessTime: Date;
    size: number;
    children?: Array<File>;}