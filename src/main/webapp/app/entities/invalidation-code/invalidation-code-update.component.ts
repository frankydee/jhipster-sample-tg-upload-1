import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IInvalidationCode } from 'app/shared/model/invalidation-code.model';
import { InvalidationCodeService } from './invalidation-code.service';

@Component({
    selector: 'jhi-invalidation-code-update',
    templateUrl: './invalidation-code-update.component.html'
})
export class InvalidationCodeUpdateComponent implements OnInit {
    private _invalidationCode: IInvalidationCode;
    isSaving: boolean;
    createDateDp: any;
    updatedDateDp: any;

    constructor(private invalidationCodeService: InvalidationCodeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ invalidationCode }) => {
            this.invalidationCode = invalidationCode;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.invalidationCode.id !== undefined) {
            this.subscribeToSaveResponse(this.invalidationCodeService.update(this.invalidationCode));
        } else {
            this.subscribeToSaveResponse(this.invalidationCodeService.create(this.invalidationCode));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IInvalidationCode>>) {
        result.subscribe((res: HttpResponse<IInvalidationCode>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get invalidationCode() {
        return this._invalidationCode;
    }

    set invalidationCode(invalidationCode: IInvalidationCode) {
        this._invalidationCode = invalidationCode;
    }
}
