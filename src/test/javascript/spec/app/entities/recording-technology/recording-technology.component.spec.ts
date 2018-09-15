/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TgUpload1TestModule } from '../../../test.module';
import { RecordingTechnologyComponent } from 'app/entities/recording-technology/recording-technology.component';
import { RecordingTechnologyService } from 'app/entities/recording-technology/recording-technology.service';
import { RecordingTechnology } from 'app/shared/model/recording-technology.model';

describe('Component Tests', () => {
    describe('RecordingTechnology Management Component', () => {
        let comp: RecordingTechnologyComponent;
        let fixture: ComponentFixture<RecordingTechnologyComponent>;
        let service: RecordingTechnologyService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [RecordingTechnologyComponent],
                providers: []
            })
                .overrideTemplate(RecordingTechnologyComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RecordingTechnologyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecordingTechnologyService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RecordingTechnology(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.recordingTechnologies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
