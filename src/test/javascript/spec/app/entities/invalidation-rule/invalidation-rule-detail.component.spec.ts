/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TgUpload1TestModule } from '../../../test.module';
import { InvalidationRuleDetailComponent } from 'app/entities/invalidation-rule/invalidation-rule-detail.component';
import { InvalidationRule } from 'app/shared/model/invalidation-rule.model';

describe('Component Tests', () => {
    describe('InvalidationRule Management Detail Component', () => {
        let comp: InvalidationRuleDetailComponent;
        let fixture: ComponentFixture<InvalidationRuleDetailComponent>;
        const route = ({ data: of({ invalidationRule: new InvalidationRule(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [InvalidationRuleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InvalidationRuleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InvalidationRuleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.invalidationRule).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
