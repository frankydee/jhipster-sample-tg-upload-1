import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IInvalidationRule } from 'app/shared/model/invalidation-rule.model';
import { InvalidationRuleService } from './invalidation-rule.service';

@Component({
    selector: 'jhi-invalidation-rule-update',
    templateUrl: './invalidation-rule-update.component.html'
})
export class InvalidationRuleUpdateComponent implements OnInit {
    private _invalidationRule: IInvalidationRule;
    isSaving: boolean;
    createDateDp: any;
    updatedDateDp: any;

    constructor(private invalidationRuleService: InvalidationRuleService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ invalidationRule }) => {
            this.invalidationRule = invalidationRule;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.invalidationRule.id !== undefined) {
            this.subscribeToSaveResponse(this.invalidationRuleService.update(this.invalidationRule));
        } else {
            this.subscribeToSaveResponse(this.invalidationRuleService.create(this.invalidationRule));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IInvalidationRule>>) {
        result.subscribe((res: HttpResponse<IInvalidationRule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get invalidationRule() {
        return this._invalidationRule;
    }

    set invalidationRule(invalidationRule: IInvalidationRule) {
        this._invalidationRule = invalidationRule;
    }
}
