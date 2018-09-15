/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TgUpload1TestModule } from '../../../test.module';
import { InvalidationCodeUpdateComponent } from 'app/entities/invalidation-code/invalidation-code-update.component';
import { InvalidationCodeService } from 'app/entities/invalidation-code/invalidation-code.service';
import { InvalidationCode } from 'app/shared/model/invalidation-code.model';

describe('Component Tests', () => {
    describe('InvalidationCode Management Update Component', () => {
        let comp: InvalidationCodeUpdateComponent;
        let fixture: ComponentFixture<InvalidationCodeUpdateComponent>;
        let service: InvalidationCodeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [InvalidationCodeUpdateComponent]
            })
                .overrideTemplate(InvalidationCodeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InvalidationCodeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvalidationCodeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new InvalidationCode(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.invalidationCode = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new InvalidationCode();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.invalidationCode = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
