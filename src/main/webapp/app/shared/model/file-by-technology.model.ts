import { Moment } from 'moment';
import { IFileType } from 'app/shared/model//file-type.model';
import { IRecordingTechnology } from 'app/shared/model//recording-technology.model';

export interface IFileByTechnology {
    id?: number;
    overridingPattern?: string;
    sortOrder?: number;
    required?: boolean;
    active?: boolean;
    createDate?: Moment;
    updatedDate?: Moment;
    fileType?: IFileType;
    technology?: IRecordingTechnology;
}

export class FileByTechnology implements IFileByTechnology {
    constructor(
        public id?: number,
        public overridingPattern?: string,
        public sortOrder?: number,
        public required?: boolean,
        public active?: boolean,
        public createDate?: Moment,
        public updatedDate?: Moment,
        public fileType?: IFileType,
        public technology?: IRecordingTechnology
    ) {
        this.required = this.required || false;
        this.active = this.active || false;
    }
}
