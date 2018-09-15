import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInvalidationCode } from 'app/shared/model/invalidation-code.model';

type EntityResponseType = HttpResponse<IInvalidationCode>;
type EntityArrayResponseType = HttpResponse<IInvalidationCode[]>;

@Injectable({ providedIn: 'root' })
export class InvalidationCodeService {
    private resourceUrl = SERVER_API_URL + 'api/invalidation-codes';

    constructor(private http: HttpClient) {}

    create(invalidationCode: IInvalidationCode): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(invalidationCode);
        return this.http
            .post<IInvalidationCode>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(invalidationCode: IInvalidationCode): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(invalidationCode);
        return this.http
            .put<IInvalidationCode>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IInvalidationCode>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IInvalidationCode[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(invalidationCode: IInvalidationCode): IInvalidationCode {
        const copy: IInvalidationCode = Object.assign({}, invalidationCode, {
            createDate:
                invalidationCode.createDate != null && invalidationCode.createDate.isValid()
                    ? invalidationCode.createDate.format(DATE_FORMAT)
                    : null,
            updatedDate:
                invalidationCode.updatedDate != null && invalidationCode.updatedDate.isValid()
                    ? invalidationCode.updatedDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createDate = res.body.createDate != null ? moment(res.body.createDate) : null;
        res.body.updatedDate = res.body.updatedDate != null ? moment(res.body.updatedDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((invalidationCode: IInvalidationCode) => {
            invalidationCode.createDate = invalidationCode.createDate != null ? moment(invalidationCode.createDate) : null;
            invalidationCode.updatedDate = invalidationCode.updatedDate != null ? moment(invalidationCode.updatedDate) : null;
        });
        return res;
    }
}
