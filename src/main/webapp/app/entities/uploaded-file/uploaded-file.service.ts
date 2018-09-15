import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUploadedFile } from 'app/shared/model/uploaded-file.model';

type EntityResponseType = HttpResponse<IUploadedFile>;
type EntityArrayResponseType = HttpResponse<IUploadedFile[]>;

@Injectable({ providedIn: 'root' })
export class UploadedFileService {
    private resourceUrl = SERVER_API_URL + 'api/uploaded-files';

    constructor(private http: HttpClient) {}

    create(uploadedFile: IUploadedFile): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(uploadedFile);
        return this.http
            .post<IUploadedFile>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(uploadedFile: IUploadedFile): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(uploadedFile);
        return this.http
            .put<IUploadedFile>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IUploadedFile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IUploadedFile[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(uploadedFile: IUploadedFile): IUploadedFile {
        const copy: IUploadedFile = Object.assign({}, uploadedFile, {
            uploadedDate:
                uploadedFile.uploadedDate != null && uploadedFile.uploadedDate.isValid() ? uploadedFile.uploadedDate.toJSON() : null,
            submittedDate:
                uploadedFile.submittedDate != null && uploadedFile.submittedDate.isValid() ? uploadedFile.submittedDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.uploadedDate = res.body.uploadedDate != null ? moment(res.body.uploadedDate) : null;
        res.body.submittedDate = res.body.submittedDate != null ? moment(res.body.submittedDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((uploadedFile: IUploadedFile) => {
            uploadedFile.uploadedDate = uploadedFile.uploadedDate != null ? moment(uploadedFile.uploadedDate) : null;
            uploadedFile.submittedDate = uploadedFile.submittedDate != null ? moment(uploadedFile.submittedDate) : null;
        });
        return res;
    }
}
