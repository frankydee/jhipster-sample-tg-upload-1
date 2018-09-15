/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TgUpload1TestModule } from '../../../test.module';
import { FileByTechnologyDetailComponent } from 'app/entities/file-by-technology/file-by-technology-detail.component';
import { FileByTechnology } from 'app/shared/model/file-by-technology.model';

describe('Component Tests', () => {
    describe('FileByTechnology Management Detail Component', () => {
        let comp: FileByTechnologyDetailComponent;
        let fixture: ComponentFixture<FileByTechnologyDetailComponent>;
        const route = ({ data: of({ fileByTechnology: new FileByTechnology(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [FileByTechnologyDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FileByTechnologyDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FileByTechnologyDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.fileByTechnology).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
