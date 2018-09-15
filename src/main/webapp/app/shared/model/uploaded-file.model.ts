import { Moment } from 'moment';

export const enum FileStatus {
    UPLOADED = 'UPLOADED',
    VALIDATING = 'VALIDATING',
    VALIDATED = 'VALIDATED',
    ERROR = 'ERROR',
    SUBMITTED = 'SUBMITTED'
}

export interface IUploadedFile {
    id?: number;
    fileName?: string;
    fileType?: string;
    uploadedDate?: Moment;
    status?: FileStatus;
    comnment?: string;
    submittedDate?: Moment;
}

export class UploadedFile implements IUploadedFile {
    constructor(
        public id?: number,
        public fileName?: string,
        public fileType?: string,
        public uploadedDate?: Moment,
        public status?: FileStatus,
        public comnment?: string,
        public submittedDate?: Moment
    ) {}
}
