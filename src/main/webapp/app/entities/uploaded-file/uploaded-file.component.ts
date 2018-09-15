import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUploadedFile } from 'app/shared/model/uploaded-file.model';
import { Principal } from 'app/core';
import { UploadedFileService } from './uploaded-file.service';

@Component({
    selector: 'jhi-uploaded-file',
    templateUrl: './uploaded-file.component.html'
})
export class UploadedFileComponent implements OnInit, OnDestroy {
    uploadedFiles: IUploadedFile[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private uploadedFileService: UploadedFileService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.uploadedFileService.query().subscribe(
            (res: HttpResponse<IUploadedFile[]>) => {
                this.uploadedFiles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInUploadedFiles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUploadedFile) {
        return item.id;
    }

    registerChangeInUploadedFiles() {
        this.eventSubscriber = this.eventManager.subscribe('uploadedFileListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
