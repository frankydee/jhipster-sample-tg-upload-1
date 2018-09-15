import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFileByTechnology } from 'app/shared/model/file-by-technology.model';
import { Principal } from 'app/core';
import { FileByTechnologyService } from './file-by-technology.service';

@Component({
    selector: 'jhi-file-by-technology',
    templateUrl: './file-by-technology.component.html'
})
export class FileByTechnologyComponent implements OnInit, OnDestroy {
    fileByTechnologies: IFileByTechnology[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private fileByTechnologyService: FileByTechnologyService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.fileByTechnologyService.query().subscribe(
            (res: HttpResponse<IFileByTechnology[]>) => {
                this.fileByTechnologies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFileByTechnologies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFileByTechnology) {
        return item.id;
    }

    registerChangeInFileByTechnologies() {
        this.eventSubscriber = this.eventManager.subscribe('fileByTechnologyListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
