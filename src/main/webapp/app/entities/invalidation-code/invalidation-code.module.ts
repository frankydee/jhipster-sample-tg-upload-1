import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TgUpload1SharedModule } from 'app/shared';
import {
    InvalidationCodeComponent,
    InvalidationCodeDetailComponent,
    InvalidationCodeUpdateComponent,
    InvalidationCodeDeletePopupComponent,
    InvalidationCodeDeleteDialogComponent,
    invalidationCodeRoute,
    invalidationCodePopupRoute
} from './';

const ENTITY_STATES = [...invalidationCodeRoute, ...invalidationCodePopupRoute];

@NgModule({
    imports: [TgUpload1SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InvalidationCodeComponent,
        InvalidationCodeDetailComponent,
        InvalidationCodeUpdateComponent,
        InvalidationCodeDeleteDialogComponent,
        InvalidationCodeDeletePopupComponent
    ],
    entryComponents: [
        InvalidationCodeComponent,
        InvalidationCodeUpdateComponent,
        InvalidationCodeDeleteDialogComponent,
        InvalidationCodeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TgUpload1InvalidationCodeModule {}
