/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TgUpload1TestModule } from '../../../test.module';
import { InvalidationRuleDeleteDialogComponent } from 'app/entities/invalidation-rule/invalidation-rule-delete-dialog.component';
import { InvalidationRuleService } from 'app/entities/invalidation-rule/invalidation-rule.service';

describe('Component Tests', () => {
    describe('InvalidationRule Management Delete Component', () => {
        let comp: InvalidationRuleDeleteDialogComponent;
        let fixture: ComponentFixture<InvalidationRuleDeleteDialogComponent>;
        let service: InvalidationRuleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [InvalidationRuleDeleteDialogComponent]
            })
                .overrideTemplate(InvalidationRuleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InvalidationRuleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvalidationRuleService);
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
