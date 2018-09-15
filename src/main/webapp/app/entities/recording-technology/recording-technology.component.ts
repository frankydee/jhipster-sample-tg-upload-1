import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRecordingTechnology } from 'app/shared/model/recording-technology.model';
import { Principal } from 'app/core';
import { RecordingTechnologyService } from './recording-technology.service';

@Component({
    selector: 'jhi-recording-technology',
    templateUrl: './recording-technology.component.html'
})
export class RecordingTechnologyComponent implements OnInit, OnDestroy {
    recordingTechnologies: IRecordingTechnology[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private recordingTechnologyService: RecordingTechnologyService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.recordingTechnologyService.query().subscribe(
            (res: HttpResponse<IRecordingTechnology[]>) => {
                this.recordingTechnologies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRecordingTechnologies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRecordingTechnology) {
        return item.id;
    }

    registerChangeInRecordingTechnologies() {
        this.eventSubscriber = this.eventManager.subscribe('recordingTechnologyListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
