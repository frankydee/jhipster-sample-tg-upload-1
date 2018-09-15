import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvalidationRule } from 'app/shared/model/invalidation-rule.model';
import { InvalidationRuleService } from './invalidation-rule.service';

@Component({
    selector: 'jhi-invalidation-rule-delete-dialog',
    templateUrl: './invalidation-rule-delete-dialog.component.html'
})
export class InvalidationRuleDeleteDialogComponent {
    invalidationRule: IInvalidationRule;

    constructor(
        private invalidationRuleService: InvalidationRuleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.invalidationRuleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'invalidationRuleListModification',
                content: 'Deleted an invalidationRule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-invalidation-rule-delete-popup',
    template: ''
})
export class InvalidationRuleDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ invalidationRule }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InvalidationRuleDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.invalidationRule = invalidationRule;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
