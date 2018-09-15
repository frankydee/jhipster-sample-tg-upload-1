/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TgUpload1TestModule } from '../../../test.module';
import { InvalidationRuleUpdateComponent } from 'app/entities/invalidation-rule/invalidation-rule-update.component';
import { InvalidationRuleService } from 'app/entities/invalidation-rule/invalidation-rule.service';
import { InvalidationRule } from 'app/shared/model/invalidation-rule.model';

describe('Component Tests', () => {
    describe('InvalidationRule Management Update Component', () => {
        let comp: InvalidationRuleUpdateComponent;
        let fixture: ComponentFixture<InvalidationRuleUpdateComponent>;
        let service: InvalidationRuleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [InvalidationRuleUpdateComponent]
            })
                .overrideTemplate(InvalidationRuleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InvalidationRuleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvalidationRuleService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new InvalidationRule(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.invalidationRule = entity;
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
                    const entity = new InvalidationRule();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.invalidationRule = entity;
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
