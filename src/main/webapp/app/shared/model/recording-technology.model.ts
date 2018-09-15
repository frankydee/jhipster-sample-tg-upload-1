import { Moment } from 'moment';

export interface IRecordingTechnology {
    id?: number;
    name?: string;
    sortOrder?: number;
    active?: boolean;
    createDate?: Moment;
    updatedDate?: Moment;
}

export class RecordingTechnology implements IRecordingTechnology {
    constructor(
        public id?: number,
        public name?: string,
        public sortOrder?: number,
        public active?: boolean,
        public createDate?: Moment,
        public updatedDate?: Moment
    ) {
        this.active = this.active || false;
    }
}
