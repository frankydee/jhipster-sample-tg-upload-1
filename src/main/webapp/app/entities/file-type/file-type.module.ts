import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TgUpload1SharedModule } from 'app/shared';
import {
    FileTypeComponent,
    FileTypeDetailComponent,
    FileTypeUpdateComponent,
    FileTypeDeletePopupComponent,
    FileTypeDeleteDialogComponent,
    fileTypeRoute,
    fileTypePopupRoute
} from './';

const ENTITY_STATES = [...fileTypeRoute, ...fileTypePopupRoute];

@NgModule({
    imports: [TgUpload1SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FileTypeComponent,
        FileTypeDetailComponent,
        FileTypeUpdateComponent,
        FileTypeDeleteDialogComponent,
        FileTypeDeletePopupComponent
    ],
    entryComponents: [FileTypeComponent, FileTypeUpdateComponent, FileTypeDeleteDialogComponent, FileTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TgUpload1FileTypeModule {}
