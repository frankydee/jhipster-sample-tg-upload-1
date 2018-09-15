/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TgUpload1TestModule } from '../../../test.module';
import { InvalidationCodeDetailComponent } from 'app/entities/invalidation-code/invalidation-code-detail.component';
import { InvalidationCode } from 'app/shared/model/invalidation-code.model';

describe('Component Tests', () => {
    describe('InvalidationCode Management Detail Component', () => {
        let comp: InvalidationCodeDetailComponent;
        let fixture: ComponentFixture<InvalidationCodeDetailComponent>;
        const route = ({ data: of({ invalidationCode: new InvalidationCode(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [InvalidationCodeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InvalidationCodeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InvalidationCodeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.invalidationCode).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
