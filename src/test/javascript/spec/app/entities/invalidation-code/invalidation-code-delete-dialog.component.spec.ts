/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TgUpload1TestModule } from '../../../test.module';
import { InvalidationCodeDeleteDialogComponent } from 'app/entities/invalidation-code/invalidation-code-delete-dialog.component';
import { InvalidationCodeService } from 'app/entities/invalidation-code/invalidation-code.service';

describe('Component Tests', () => {
    describe('InvalidationCode Management Delete Component', () => {
        let comp: InvalidationCodeDeleteDialogComponent;
        let fixture: ComponentFixture<InvalidationCodeDeleteDialogComponent>;
        let service: InvalidationCodeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [InvalidationCodeDeleteDialogComponent]
            })
                .overrideTemplate(InvalidationCodeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InvalidationCodeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvalidationCodeService);
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
