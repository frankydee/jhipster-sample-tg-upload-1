import { Moment } from 'moment';

export interface IFileByTechnology {
    id?: number;
    overridingPattern?: string;
    sortOrder?: number;
    required?: boolean;
    active?: boolean;
    createDate?: Moment;
    updatedDate?: Moment;
}

export class FileByTechnology implements IFileByTechnology {
    constructor(
        public id?: number,
        public overridingPattern?: string,
        public sortOrder?: number,
        public required?: boolean,
        public active?: boolean,
        public createDate?: Moment,
        public updatedDate?: Moment
    ) {
        this.required = this.required || false;
        this.active = this.active || false;
    }
}
