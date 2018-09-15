import { Moment } from 'moment';

export interface IFileType {
    id?: number;
    name?: string;
    description?: string;
    defaultPattern?: string;
    active?: boolean;
    createDate?: Moment;
    updatedDate?: Moment;
}

export class FileType implements IFileType {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public defaultPattern?: string,
        public active?: boolean,
        public createDate?: Moment,
        public updatedDate?: Moment
    ) {
        this.active = this.active || false;
    }
}
