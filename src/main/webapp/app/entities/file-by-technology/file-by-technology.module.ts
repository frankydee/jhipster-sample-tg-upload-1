import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TgUpload1SharedModule } from 'app/shared';
import {
    FileByTechnologyComponent,
    FileByTechnologyDetailComponent,
    FileByTechnologyUpdateComponent,
    FileByTechnologyDeletePopupComponent,
    FileByTechnologyDeleteDialogComponent,
    fileByTechnologyRoute,
    fileByTechnologyPopupRoute
} from './';

const ENTITY_STATES = [...fileByTechnologyRoute, ...fileByTechnologyPopupRoute];

@NgModule({
    imports: [TgUpload1SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FileByTechnologyComponent,
        FileByTechnologyDetailComponent,
        FileByTechnologyUpdateComponent,
        FileByTechnologyDeleteDialogComponent,
        FileByTechnologyDeletePopupComponent
    ],
    entryComponents: [
        FileByTechnologyComponent,
        FileByTechnologyUpdateComponent,
        FileByTechnologyDeleteDialogComponent,
        FileByTechnologyDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TgUpload1FileByTechnologyModule {}
