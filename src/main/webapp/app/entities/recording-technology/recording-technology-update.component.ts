import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRecordingTechnology } from 'app/shared/model/recording-technology.model';
import { RecordingTechnologyService } from './recording-technology.service';

@Component({
    selector: 'jhi-recording-technology-update',
    templateUrl: './recording-technology-update.component.html'
})
export class RecordingTechnologyUpdateComponent implements OnInit {
    private _recordingTechnology: IRecordingTechnology;
    isSaving: boolean;
    createDateDp: any;
    updatedDateDp: any;

    constructor(private recordingTechnologyService: RecordingTechnologyService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recordingTechnology }) => {
            this.recordingTechnology = recordingTechnology;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recordingTechnology.id !== undefined) {
            this.subscribeToSaveResponse(this.recordingTechnologyService.update(this.recordingTechnology));
        } else {
            this.subscribeToSaveResponse(this.recordingTechnologyService.create(this.recordingTechnology));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRecordingTechnology>>) {
        result.subscribe((res: HttpResponse<IRecordingTechnology>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get recordingTechnology() {
        return this._recordingTechnology;
    }

    set recordingTechnology(recordingTechnology: IRecordingTechnology) {
        this._recordingTechnology = recordingTechnology;
    }
}
