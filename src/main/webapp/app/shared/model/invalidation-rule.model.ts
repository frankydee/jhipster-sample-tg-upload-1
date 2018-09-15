import { Moment } from 'moment';

export interface IInvalidationRule {
    id?: number;
    elr?: string;
    trackId?: string;
    startMileage?: number;
    endMileage?: number;
    comment?: string;
    createDate?: Moment;
    updatedDate?: Moment;
}

export class InvalidationRule implements IInvalidationRule {
    constructor(
        public id?: number,
        public elr?: string,
        public trackId?: string,
        public startMileage?: number,
        public endMileage?: number,
        public comment?: string,
        public createDate?: Moment,
        public updatedDate?: Moment
    ) {}
}
