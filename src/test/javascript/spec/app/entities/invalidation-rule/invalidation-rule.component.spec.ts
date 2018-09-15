/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TgUpload1TestModule } from '../../../test.module';
import { InvalidationRuleComponent } from 'app/entities/invalidation-rule/invalidation-rule.component';
import { InvalidationRuleService } from 'app/entities/invalidation-rule/invalidation-rule.service';
import { InvalidationRule } from 'app/shared/model/invalidation-rule.model';

describe('Component Tests', () => {
    describe('InvalidationRule Management Component', () => {
        let comp: InvalidationRuleComponent;
        let fixture: ComponentFixture<InvalidationRuleComponent>;
        let service: InvalidationRuleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [InvalidationRuleComponent],
                providers: []
            })
                .overrideTemplate(InvalidationRuleComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InvalidationRuleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvalidationRuleService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new InvalidationRule(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.invalidationRules[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
