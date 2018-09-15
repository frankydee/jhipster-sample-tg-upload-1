import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IFileType } from 'app/shared/model/file-type.model';
import { FileTypeService } from './file-type.service';

@Component({
    selector: 'jhi-file-type-update',
    templateUrl: './file-type-update.component.html'
})
export class FileTypeUpdateComponent implements OnInit {
    private _fileType: IFileType;
    isSaving: boolean;
    createDateDp: any;
    updatedDateDp: any;

    constructor(private fileTypeService: FileTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fileType }) => {
            this.fileType = fileType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.fileType.id !== undefined) {
            this.subscribeToSaveResponse(this.fileTypeService.update(this.fileType));
        } else {
            this.subscribeToSaveResponse(this.fileTypeService.create(this.fileType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFileType>>) {
        result.subscribe((res: HttpResponse<IFileType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get fileType() {
        return this._fileType;
    }

    set fileType(fileType: IFileType) {
        this._fileType = fileType;
    }
}
