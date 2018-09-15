import { Moment } from 'moment';

export interface IInvalidationCode {
    id?: number;
    code?: number;
    shortDescription?: string;
    longDescription?: string;
    active?: boolean;
    createDate?: Moment;
    updatedDate?: Moment;
}

export class InvalidationCode implements IInvalidationCode {
    constructor(
        public id?: number,
        public code?: number,
        public shortDescription?: string,
        public longDescription?: string,
        public active?: boolean,
        public createDate?: Moment,
        public updatedDate?: Moment
    ) {
        this.active = this.active || false;
    }
}
