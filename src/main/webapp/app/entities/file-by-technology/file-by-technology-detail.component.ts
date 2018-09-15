import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFileByTechnology } from 'app/shared/model/file-by-technology.model';

@Component({
    selector: 'jhi-file-by-technology-detail',
    templateUrl: './file-by-technology-detail.component.html'
})
export class FileByTechnologyDetailComponent implements OnInit {
    fileByTechnology: IFileByTechnology;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fileByTechnology }) => {
            this.fileByTechnology = fileByTechnology;
        });
    }

    previousState() {
        window.history.back();
    }
}
