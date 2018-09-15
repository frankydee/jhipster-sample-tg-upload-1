import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFileByTechnology } from 'app/shared/model/file-by-technology.model';
import { FileByTechnologyService } from './file-by-technology.service';

@Component({
    selector: 'jhi-file-by-technology-delete-dialog',
    templateUrl: './file-by-technology-delete-dialog.component.html'
})
export class FileByTechnologyDeleteDialogComponent {
    fileByTechnology: IFileByTechnology;

    constructor(
        private fileByTechnologyService: FileByTechnologyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fileByTechnologyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'fileByTechnologyListModification',
                content: 'Deleted an fileByTechnology'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-file-by-technology-delete-popup',
    template: ''
})
export class FileByTechnologyDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fileByTechnology }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FileByTechnologyDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.fileByTechnology = fileByTechnology;
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
