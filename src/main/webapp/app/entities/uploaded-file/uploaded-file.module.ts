import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TgUpload1SharedModule } from 'app/shared';
import {
    UploadedFileComponent,
    UploadedFileDetailComponent,
    UploadedFileUpdateComponent,
    UploadedFileDeletePopupComponent,
    UploadedFileDeleteDialogComponent,
    uploadedFileRoute,
    uploadedFilePopupRoute
} from './';

const ENTITY_STATES = [...uploadedFileRoute, ...uploadedFilePopupRoute];

@NgModule({
    imports: [TgUpload1SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UploadedFileComponent,
        UploadedFileDetailComponent,
        UploadedFileUpdateComponent,
        UploadedFileDeleteDialogComponent,
        UploadedFileDeletePopupComponent
    ],
    entryComponents: [
        UploadedFileComponent,
        UploadedFileUpdateComponent,
        UploadedFileDeleteDialogComponent,
        UploadedFileDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TgUpload1UploadedFileModule {}
