/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TgUpload1TestModule } from '../../../test.module';
import { FileByTechnologyDeleteDialogComponent } from 'app/entities/file-by-technology/file-by-technology-delete-dialog.component';
import { FileByTechnologyService } from 'app/entities/file-by-technology/file-by-technology.service';

describe('Component Tests', () => {
    describe('FileByTechnology Management Delete Component', () => {
        let comp: FileByTechnologyDeleteDialogComponent;
        let fixture: ComponentFixture<FileByTechnologyDeleteDialogComponent>;
        let service: FileByTechnologyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [FileByTechnologyDeleteDialogComponent]
            })
                .overrideTemplate(FileByTechnologyDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FileByTechnologyDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FileByTechnologyService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
