/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TgUpload1TestModule } from '../../../test.module';
import { RecordingTechnologyDeleteDialogComponent } from 'app/entities/recording-technology/recording-technology-delete-dialog.component';
import { RecordingTechnologyService } from 'app/entities/recording-technology/recording-technology.service';

describe('Component Tests', () => {
    describe('RecordingTechnology Management Delete Component', () => {
        let comp: RecordingTechnologyDeleteDialogComponent;
        let fixture: ComponentFixture<RecordingTechnologyDeleteDialogComponent>;
        let service: RecordingTechnologyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [RecordingTechnologyDeleteDialogComponent]
            })
                .overrideTemplate(RecordingTechnologyDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecordingTechnologyDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecordingTechnologyService);
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
