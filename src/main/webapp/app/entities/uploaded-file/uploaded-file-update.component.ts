import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUploadedFile } from 'app/shared/model/uploaded-file.model';
import { UploadedFileService } from './uploaded-file.service';

@Component({
    selector: 'jhi-uploaded-file-update',
    templateUrl: './uploaded-file-update.component.html'
})
export class UploadedFileUpdateComponent implements OnInit {
    private _uploadedFile: IUploadedFile;
    isSaving: boolean;
    uploadedDate: string;
    submittedDate: string;

    constructor(private uploadedFileService: UploadedFileService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ uploadedFile }) => {
            this.uploadedFile = uploadedFile;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.uploadedFile.uploadedDate = moment(this.uploadedDate, DATE_TIME_FORMAT);
        this.uploadedFile.submittedDate = moment(this.submittedDate, DATE_TIME_FORMAT);
        if (this.uploadedFile.id !== undefined) {
            this.subscribeToSaveResponse(this.uploadedFileService.update(this.uploadedFile));
        } else {
            this.subscribeToSaveResponse(this.uploadedFileService.create(this.uploadedFile));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUploadedFile>>) {
        result.subscribe((res: HttpResponse<IUploadedFile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get uploadedFile() {
        return this._uploadedFile;
    }

    set uploadedFile(uploadedFile: IUploadedFile) {
        this._uploadedFile = uploadedFile;
        this.uploadedDate = moment(uploadedFile.uploadedDate).format(DATE_TIME_FORMAT);
        this.submittedDate = moment(uploadedFile.submittedDate).format(DATE_TIME_FORMAT);
    }
}
