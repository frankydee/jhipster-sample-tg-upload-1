/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TgUpload1TestModule } from '../../../test.module';
import { RecordingTechnologyDetailComponent } from 'app/entities/recording-technology/recording-technology-detail.component';
import { RecordingTechnology } from 'app/shared/model/recording-technology.model';

describe('Component Tests', () => {
    describe('RecordingTechnology Management Detail Component', () => {
        let comp: RecordingTechnologyDetailComponent;
        let fixture: ComponentFixture<RecordingTechnologyDetailComponent>;
        const route = ({ data: of({ recordingTechnology: new RecordingTechnology(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [RecordingTechnologyDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RecordingTechnologyDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecordingTechnologyDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.recordingTechnology).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
