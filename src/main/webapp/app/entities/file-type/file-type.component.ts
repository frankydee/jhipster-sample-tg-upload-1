import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFileType } from 'app/shared/model/file-type.model';
import { Principal } from 'app/core';
import { FileTypeService } from './file-type.service';

@Component({
    selector: 'jhi-file-type',
    templateUrl: './file-type.component.html'
})
export class FileTypeComponent implements OnInit, OnDestroy {
    fileTypes: IFileType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private fileTypeService: FileTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.fileTypeService.query().subscribe(
            (res: HttpResponse<IFileType[]>) => {
                this.fileTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFileTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFileType) {
        return item.id;
    }

    registerChangeInFileTypes() {
        this.eventSubscriber = this.eventManager.subscribe('fileTypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
