import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUploadedFile } from 'app/shared/model/uploaded-file.model';
import { UploadedFileService } from './uploaded-file.service';

@Component({
    selector: 'jhi-uploaded-file-delete-dialog',
    templateUrl: './uploaded-file-delete-dialog.component.html'
})
export class UploadedFileDeleteDialogComponent {
    uploadedFile: IUploadedFile;

    constructor(
        private uploadedFileService: UploadedFileService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.uploadedFileService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'uploadedFileListModification',
                content: 'Deleted an uploadedFile'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-uploaded-file-delete-popup',
    template: ''
})
export class UploadedFileDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ uploadedFile }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UploadedFileDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.uploadedFile = uploadedFile;
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
