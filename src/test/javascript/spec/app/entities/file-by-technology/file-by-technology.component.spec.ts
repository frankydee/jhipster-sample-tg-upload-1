/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TgUpload1TestModule } from '../../../test.module';
import { FileByTechnologyComponent } from 'app/entities/file-by-technology/file-by-technology.component';
import { FileByTechnologyService } from 'app/entities/file-by-technology/file-by-technology.service';
import { FileByTechnology } from 'app/shared/model/file-by-technology.model';

describe('Component Tests', () => {
    describe('FileByTechnology Management Component', () => {
        let comp: FileByTechnologyComponent;
        let fixture: ComponentFixture<FileByTechnologyComponent>;
        let service: FileByTechnologyService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [FileByTechnologyComponent],
                providers: []
            })
                .overrideTemplate(FileByTechnologyComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FileByTechnologyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FileByTechnologyService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FileByTechnology(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.fileByTechnologies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
