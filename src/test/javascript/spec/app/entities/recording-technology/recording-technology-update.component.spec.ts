/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TgUpload1TestModule } from '../../../test.module';
import { RecordingTechnologyUpdateComponent } from 'app/entities/recording-technology/recording-technology-update.component';
import { RecordingTechnologyService } from 'app/entities/recording-technology/recording-technology.service';
import { RecordingTechnology } from 'app/shared/model/recording-technology.model';

describe('Component Tests', () => {
    describe('RecordingTechnology Management Update Component', () => {
        let comp: RecordingTechnologyUpdateComponent;
        let fixture: ComponentFixture<RecordingTechnologyUpdateComponent>;
        let service: RecordingTechnologyService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [RecordingTechnologyUpdateComponent]
            })
                .overrideTemplate(RecordingTechnologyUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RecordingTechnologyUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecordingTechnologyService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RecordingTechnology(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recordingTechnology = entity;
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
                    const entity = new RecordingTechnology();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recordingTechnology = entity;
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
