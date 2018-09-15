import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecordingTechnology } from 'app/shared/model/recording-technology.model';

@Component({
    selector: 'jhi-recording-technology-detail',
    templateUrl: './recording-technology-detail.component.html'
})
export class RecordingTechnologyDetailComponent implements OnInit {
    recordingTechnology: IRecordingTechnology;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recordingTechnology }) => {
            this.recordingTechnology = recordingTechnology;
        });
    }

    previousState() {
        window.history.back();
    }
}
