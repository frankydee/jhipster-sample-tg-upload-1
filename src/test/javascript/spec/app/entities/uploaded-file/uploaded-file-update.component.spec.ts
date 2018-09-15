/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TgUpload1TestModule } from '../../../test.module';
import { UploadedFileUpdateComponent } from 'app/entities/uploaded-file/uploaded-file-update.component';
import { UploadedFileService } from 'app/entities/uploaded-file/uploaded-file.service';
import { UploadedFile } from 'app/shared/model/uploaded-file.model';

describe('Component Tests', () => {
    describe('UploadedFile Management Update Component', () => {
        let comp: UploadedFileUpdateComponent;
        let fixture: ComponentFixture<UploadedFileUpdateComponent>;
        let service: UploadedFileService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [UploadedFileUpdateComponent]
            })
                .overrideTemplate(UploadedFileUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UploadedFileUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UploadedFileService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new UploadedFile(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.uploadedFile = entity;
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
                    const entity = new UploadedFile();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.uploadedFile = entity;
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
