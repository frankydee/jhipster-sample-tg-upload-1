import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFileByTechnology } from 'app/shared/model/file-by-technology.model';

type EntityResponseType = HttpResponse<IFileByTechnology>;
type EntityArrayResponseType = HttpResponse<IFileByTechnology[]>;

@Injectable({ providedIn: 'root' })
export class FileByTechnologyService {
    private resourceUrl = SERVER_API_URL + 'api/file-by-technologies';

    constructor(private http: HttpClient) {}

    create(fileByTechnology: IFileByTechnology): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(fileByTechnology);
        return this.http
            .post<IFileByTechnology>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(fileByTechnology: IFileByTechnology): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(fileByTechnology);
        return this.http
            .put<IFileByTechnology>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFileByTechnology>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFileByTechnology[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(fileByTechnology: IFileByTechnology): IFileByTechnology {
        const copy: IFileByTechnology = Object.assign({}, fileByTechnology, {
            createDate:
                fileByTechnology.createDate != null && fileByTechnology.createDate.isValid()
                    ? fileByTechnology.createDate.format(DATE_FORMAT)
                    : null,
            updatedDate:
                fileByTechnology.updatedDate != null && fileByTechnology.updatedDate.isValid()
                    ? fileByTechnology.updatedDate.format(DATE_FORMAT)
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
        res.body.forEach((fileByTechnology: IFileByTechnology) => {
            fileByTechnology.createDate = fileByTechnology.createDate != null ? moment(fileByTechnology.createDate) : null;
            fileByTechnology.updatedDate = fileByTechnology.updatedDate != null ? moment(fileByTechnology.updatedDate) : null;
        });
        return res;
    }
}
