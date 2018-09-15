import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecordingTechnology } from 'app/shared/model/recording-technology.model';

type EntityResponseType = HttpResponse<IRecordingTechnology>;
type EntityArrayResponseType = HttpResponse<IRecordingTechnology[]>;

@Injectable({ providedIn: 'root' })
export class RecordingTechnologyService {
    private resourceUrl = SERVER_API_URL + 'api/recording-technologies';

    constructor(private http: HttpClient) {}

    create(recordingTechnology: IRecordingTechnology): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(recordingTechnology);
        return this.http
            .post<IRecordingTechnology>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(recordingTechnology: IRecordingTechnology): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(recordingTechnology);
        return this.http
            .put<IRecordingTechnology>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRecordingTechnology>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRecordingTechnology[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(recordingTechnology: IRecordingTechnology): IRecordingTechnology {
        const copy: IRecordingTechnology = Object.assign({}, recordingTechnology, {
            createDate:
                recordingTechnology.createDate != null && recordingTechnology.createDate.isValid()
                    ? recordingTechnology.createDate.format(DATE_FORMAT)
                    : null,
            updatedDate:
                recordingTechnology.updatedDate != null && recordingTechnology.updatedDate.isValid()
                    ? recordingTechnology.updatedDate.format(DATE_FORMAT)
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
        res.body.forEach((recordingTechnology: IRecordingTechnology) => {
            recordingTechnology.createDate = recordingTechnology.createDate != null ? moment(recordingTechnology.createDate) : null;
            recordingTechnology.updatedDate = recordingTechnology.updatedDate != null ? moment(recordingTechnology.updatedDate) : null;
        });
        return res;
    }
}
