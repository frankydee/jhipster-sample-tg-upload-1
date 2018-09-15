/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TgUpload1TestModule } from '../../../test.module';
import { FileByTechnologyUpdateComponent } from 'app/entities/file-by-technology/file-by-technology-update.component';
import { FileByTechnologyService } from 'app/entities/file-by-technology/file-by-technology.service';
import { FileByTechnology } from 'app/shared/model/file-by-technology.model';

describe('Component Tests', () => {
    describe('FileByTechnology Management Update Component', () => {
        let comp: FileByTechnologyUpdateComponent;
        let fixture: ComponentFixture<FileByTechnologyUpdateComponent>;
        let service: FileByTechnologyService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [FileByTechnologyUpdateComponent]
            })
                .overrideTemplate(FileByTechnologyUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FileByTechnologyUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FileByTechnologyService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new FileByTechnology(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.fileByTechnology = entity;
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
                    const entity = new FileByTechnology();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.fileByTechnology = entity;
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
