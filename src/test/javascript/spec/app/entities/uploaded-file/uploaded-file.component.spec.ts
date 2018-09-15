/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TgUpload1TestModule } from '../../../test.module';
import { UploadedFileComponent } from 'app/entities/uploaded-file/uploaded-file.component';
import { UploadedFileService } from 'app/entities/uploaded-file/uploaded-file.service';
import { UploadedFile } from 'app/shared/model/uploaded-file.model';

describe('Component Tests', () => {
    describe('UploadedFile Management Component', () => {
        let comp: UploadedFileComponent;
        let fixture: ComponentFixture<UploadedFileComponent>;
        let service: UploadedFileService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [UploadedFileComponent],
                providers: []
            })
                .overrideTemplate(UploadedFileComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UploadedFileComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UploadedFileService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new UploadedFile(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.uploadedFiles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
