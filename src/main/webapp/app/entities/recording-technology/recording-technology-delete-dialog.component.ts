import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecordingTechnology } from 'app/shared/model/recording-technology.model';
import { RecordingTechnologyService } from './recording-technology.service';

@Component({
    selector: 'jhi-recording-technology-delete-dialog',
    templateUrl: './recording-technology-delete-dialog.component.html'
})
export class RecordingTechnologyDeleteDialogComponent {
    recordingTechnology: IRecordingTechnology;

    constructor(
        private recordingTechnologyService: RecordingTechnologyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recordingTechnologyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'recordingTechnologyListModification',
                content: 'Deleted an recordingTechnology'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recording-technology-delete-popup',
    template: ''
})
export class RecordingTechnologyDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recordingTechnology }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RecordingTechnologyDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.recordingTechnology = recordingTechnology;
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
