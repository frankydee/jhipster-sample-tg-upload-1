import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFileByTechnology } from 'app/shared/model/file-by-technology.model';
import { FileByTechnologyService } from './file-by-technology.service';
import { IFileType } from 'app/shared/model/file-type.model';
import { FileTypeService } from 'app/entities/file-type';
import { IRecordingTechnology } from 'app/shared/model/recording-technology.model';
import { RecordingTechnologyService } from 'app/entities/recording-technology';

@Component({
    selector: 'jhi-file-by-technology-update',
    templateUrl: './file-by-technology-update.component.html'
})
export class FileByTechnologyUpdateComponent implements OnInit {
    private _fileByTechnology: IFileByTechnology;
    isSaving: boolean;

    filetypes: IFileType[];

    recordingtechnologies: IRecordingTechnology[];
    createDateDp: any;
    updatedDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private fileByTechnologyService: FileByTechnologyService,
        private fileTypeService: FileTypeService,
        private recordingTechnologyService: RecordingTechnologyService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fileByTechnology }) => {
            this.fileByTechnology = fileByTechnology;
        });
        this.fileTypeService.query().subscribe(
            (res: HttpResponse<IFileType[]>) => {
                this.filetypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.recordingTechnologyService.query().subscribe(
            (res: HttpResponse<IRecordingTechnology[]>) => {
                this.recordingtechnologies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackFileTypeById(index: number, item: IFileType) {
        return item.id;
    }

    trackRecordingTechnologyById(index: number, item: IRecordingTechnology) {
        return item.id;
    }
    get fileByTechnology() {
        return this._fileByTechnology;
    }

    set fileByTechnology(fileByTechnology: IFileByTechnology) {
        this._fileByTechnology = fileByTechnology;
    }
}
