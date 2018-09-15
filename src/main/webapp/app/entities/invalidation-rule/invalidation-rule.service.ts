import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInvalidationRule } from 'app/shared/model/invalidation-rule.model';

type EntityResponseType = HttpResponse<IInvalidationRule>;
type EntityArrayResponseType = HttpResponse<IInvalidationRule[]>;

@Injectable({ providedIn: 'root' })
export class InvalidationRuleService {
    private resourceUrl = SERVER_API_URL + 'api/invalidation-rules';

    constructor(private http: HttpClient) {}

    create(invalidationRule: IInvalidationRule): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(invalidationRule);
        return this.http
            .post<IInvalidationRule>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(invalidationRule: IInvalidationRule): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(invalidationRule);
        return this.http
            .put<IInvalidationRule>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IInvalidationRule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IInvalidationRule[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(invalidationRule: IInvalidationRule): IInvalidationRule {
        const copy: IInvalidationRule = Object.assign({}, invalidationRule, {
            createDate:
                invalidationRule.createDate != null && invalidationRule.createDate.isValid()
                    ? invalidationRule.createDate.format(DATE_FORMAT)
                    : null,
            updatedDate:
                invalidationRule.updatedDate != null && invalidationRule.updatedDate.isValid()
                    ? invalidationRule.updatedDate.format(DATE_FORMAT)
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
        res.body.forEach((invalidationRule: IInvalidationRule) => {
            invalidationRule.createDate = invalidationRule.createDate != null ? moment(invalidationRule.createDate) : null;
            invalidationRule.updatedDate = invalidationRule.updatedDate != null ? moment(invalidationRule.updatedDate) : null;
        });
        return res;
    }
}
