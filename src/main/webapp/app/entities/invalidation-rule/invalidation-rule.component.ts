import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInvalidationRule } from 'app/shared/model/invalidation-rule.model';
import { Principal } from 'app/core';
import { InvalidationRuleService } from './invalidation-rule.service';

@Component({
    selector: 'jhi-invalidation-rule',
    templateUrl: './invalidation-rule.component.html'
})
export class InvalidationRuleComponent implements OnInit, OnDestroy {
    invalidationRules: IInvalidationRule[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private invalidationRuleService: InvalidationRuleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.invalidationRuleService.query().subscribe(
            (res: HttpResponse<IInvalidationRule[]>) => {
                this.invalidationRules = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInInvalidationRules();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IInvalidationRule) {
        return item.id;
    }

    registerChangeInInvalidationRules() {
        this.eventSubscriber = this.eventManager.subscribe('invalidationRuleListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
