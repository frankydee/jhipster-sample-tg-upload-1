import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBatch } from 'app/shared/model/batch.model';

type EntityResponseType = HttpResponse<IBatch>;
type EntityArrayResponseType = HttpResponse<IBatch[]>;

@Injectable({ providedIn: 'root' })
export class BatchService {
    private resourceUrl = SERVER_API_URL + 'api/batches';

    constructor(private http: HttpClient) {}

    create(batch: IBatch): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(batch);
        return this.http
            .post<IBatch>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(batch: IBatch): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(batch);
        return this.http
            .put<IBatch>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBatch>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBatch[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(batch: IBatch): IBatch {
        const copy: IBatch = Object.assign({}, batch, {
            initiatedDate: batch.initiatedDate != null && batch.initiatedDate.isValid() ? batch.initiatedDate.format(DATE_FORMAT) : null,
            modifiedDate: batch.modifiedDate != null && batch.modifiedDate.isValid() ? batch.modifiedDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.initiatedDate = res.body.initiatedDate != null ? moment(res.body.initiatedDate) : null;
        res.body.modifiedDate = res.body.modifiedDate != null ? moment(res.body.modifiedDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((batch: IBatch) => {
            batch.initiatedDate = batch.initiatedDate != null ? moment(batch.initiatedDate) : null;
            batch.modifiedDate = batch.modifiedDate != null ? moment(batch.modifiedDate) : null;
        });
        return res;
    }
}
