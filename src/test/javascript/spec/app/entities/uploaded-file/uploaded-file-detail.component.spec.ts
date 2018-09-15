/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TgUpload1TestModule } from '../../../test.module';
import { UploadedFileDetailComponent } from 'app/entities/uploaded-file/uploaded-file-detail.component';
import { UploadedFile } from 'app/shared/model/uploaded-file.model';

describe('Component Tests', () => {
    describe('UploadedFile Management Detail Component', () => {
        let comp: UploadedFileDetailComponent;
        let fixture: ComponentFixture<UploadedFileDetailComponent>;
        const route = ({ data: of({ uploadedFile: new UploadedFile(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TgUpload1TestModule],
                declarations: [UploadedFileDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UploadedFileDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UploadedFileDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.uploadedFile).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
