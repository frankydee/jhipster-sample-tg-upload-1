import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFileType } from 'app/shared/model/file-type.model';

type EntityResponseType = HttpResponse<IFileType>;
type EntityArrayResponseType = HttpResponse<IFileType[]>;

@Injectable({ providedIn: 'root' })
export class FileTypeService {
    private resourceUrl = SERVER_API_URL + 'api/file-types';

    constructor(private http: HttpClient) {}

    create(fileType: IFileType): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(fileType);
        return this.http
            .post<IFileType>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(fileType: IFileType): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(fileType);
        return this.http
            .put<IFileType>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFileType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFileType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(fileType: IFileType): IFileType {
        const copy: IFileType = Object.assign({}, fileType, {
            createDate: fileType.createDate != null && fileType.createDate.isValid() ? fileType.createDate.format(DATE_FORMAT) : null,
            updatedDate: fileType.updatedDate != null && fileType.updatedDate.isValid() ? fileType.updatedDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createDate = res.body.createDate != null ? moment(res.body.createDate) : null;
        res.body.updatedDate = res.body.updatedDate != null ? moment(res.body.updatedDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((fileType: IFileType) => {
            fileType.createDate = fileType.createDate != null ? moment(fileType.createDate) : null;
            fileType.updatedDate = fileType.updatedDate != null ? moment(fileType.updatedDate) : null;
        });
        return res;
    }
}
