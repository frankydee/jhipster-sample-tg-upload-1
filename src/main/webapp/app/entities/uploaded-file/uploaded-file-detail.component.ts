import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUploadedFile } from 'app/shared/model/uploaded-file.model';

@Component({
    selector: 'jhi-uploaded-file-detail',
    templateUrl: './uploaded-file-detail.component.html'
})
export class UploadedFileDetailComponent implements OnInit {
    uploadedFile: IUploadedFile;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ uploadedFile }) => {
            this.uploadedFile = uploadedFile;
        });
    }

    previousState() {
        window.history.back();
    }
}
