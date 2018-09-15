/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TgUpload1TestModule } from '../../../test.module';
import { FileTypeComponent } from 'app/entities/file-type/file-type.component';
import { FileTypeService } from 'app/entities/file-type/file-type.service';
import { FileType } from 'app/shared/model/file-type.model';

describe('Component Tests', () => {
    describe('FileType Management Component', () => {
        let comp: FileTypeComponent;
        let fixture: ComponentFixture<FileTypeComponent>;
        let service: FileTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [FileTypeComponent],
                providers: []
            })
                .overrideTemplate(FileTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FileTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FileTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FileType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.fileTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
