import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvalidationCode } from 'app/shared/model/invalidation-code.model';
import { InvalidationCodeService } from './invalidation-code.service';

@Component({
    selector: 'jhi-invalidation-code-delete-dialog',
    templateUrl: './invalidation-code-delete-dialog.component.html'
})
export class InvalidationCodeDeleteDialogComponent {
    invalidationCode: IInvalidationCode;

    constructor(
        private invalidationCodeService: InvalidationCodeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.invalidationCodeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'invalidationCodeListModification',
                content: 'Deleted an invalidationCode'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-invalidation-code-delete-popup',
    template: ''
})
export class InvalidationCodeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ invalidationCode }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InvalidationCodeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.invalidationCode = invalidationCode;
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
