import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IFileByTechnology } from 'app/shared/model/file-by-technology.model';
import { FileByTechnologyService } from './file-by-technology.service';

@Component({
    selector: 'jhi-file-by-technology-update',
    templateUrl: './file-by-technology-update.component.html'
})
export class FileByTechnologyUpdateComponent implements OnInit {
    private _fileByTechnology: IFileByTechnology;
    isSaving: boolean;
    createDateDp: any;
    updatedDateDp: any;

    constructor(private fileByTechnologyService: FileByTechnologyService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fileByTechnology }) => {
            this.fileByTechnology = fileByTechnology;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.fileByTechnology.id !== undefined) {
            this.subscribeToSaveResponse(this.fileByTechnologyService.update(this.fileByTechnology));
        } else {
            this.subscribeToSaveResponse(this.fileByTechnologyService.create(this.fileByTechnology));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFileByTechnology>>) {
        result.subscribe((res: HttpResponse<IFileByTechnology>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get fileByTechnology() {
        return this._fileByTechnology;
    }

    set fileByTechnology(fileByTechnology: IFileByTechnology) {
        this._fileByTechnology = fileByTechnology;
    }
}
