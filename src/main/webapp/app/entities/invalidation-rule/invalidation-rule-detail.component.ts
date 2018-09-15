import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvalidationRule } from 'app/shared/model/invalidation-rule.model';

@Component({
    selector: 'jhi-invalidation-rule-detail',
    templateUrl: './invalidation-rule-detail.component.html'
})
export class InvalidationRuleDetailComponent implements OnInit {
    invalidationRule: IInvalidationRule;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ invalidationRule }) => {
            this.invalidationRule = invalidationRule;
        });
    }

    previousState() {
        window.history.back();
    }
}
