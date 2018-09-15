import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvalidationCode } from 'app/shared/model/invalidation-code.model';

@Component({
    selector: 'jhi-invalidation-code-detail',
    templateUrl: './invalidation-code-detail.component.html'
})
export class InvalidationCodeDetailComponent implements OnInit {
    invalidationCode: IInvalidationCode;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ invalidationCode }) => {
            this.invalidationCode = invalidationCode;
        });
    }

    previousState() {
        window.history.back();
    }
}
