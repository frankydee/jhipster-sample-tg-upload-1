import { Moment } from 'moment';

export const enum BatchStatus {
    OPEN = 'OPEN',
    ERROR = 'ERROR',
    SUBMITTED = 'SUBMITTED',
    CANCELLED = 'CANCELLED'
}

export interface IBatch {
    id?: number;
    initiatedDate?: Moment;
    username?: string;
    comment?: string;
    trainIdentifier?: string;
    journey?: string;
    status?: BatchStatus;
    modifiedDate?: Moment;
}

export class Batch implements IBatch {
    constructor(
        public id?: number,
        public initiatedDate?: Moment,
        public username?: string,
        public comment?: string,
        public trainIdentifier?: string,
        public journey?: string,
        public status?: BatchStatus,
        public modifiedDate?: Moment
    ) {}
}
