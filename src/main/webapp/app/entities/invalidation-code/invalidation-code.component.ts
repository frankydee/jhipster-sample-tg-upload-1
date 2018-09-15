import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInvalidationCode } from 'app/shared/model/invalidation-code.model';
import { Principal } from 'app/core';
import { InvalidationCodeService } from './invalidation-code.service';

@Component({
    selector: 'jhi-invalidation-code',
    templateUrl: './invalidation-code.component.html'
})
export class InvalidationCodeComponent implements OnInit, OnDestroy {
    invalidationCodes: IInvalidationCode[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private invalidationCodeService: InvalidationCodeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.invalidationCodeService.query().subscribe(
            (res: HttpResponse<IInvalidationCode[]>) => {
                this.invalidationCodes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInInvalidationCodes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IInvalidationCode) {
        return item.id;
    }

    registerChangeInInvalidationCodes() {
        this.eventSubscriber = this.eventManager.subscribe('invalidationCodeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
