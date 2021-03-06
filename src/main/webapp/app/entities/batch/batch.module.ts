import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TgUpload1SharedModule } from 'app/shared';
import {
    BatchComponent,
    BatchDetailComponent,
    BatchUpdateComponent,
    BatchDeletePopupComponent,
    BatchDeleteDialogComponent,
    batchRoute,
    batchPopupRoute
} from './';

const ENTITY_STATES = [...batchRoute, ...batchPopupRoute];

@NgModule({
    imports: [TgUpload1SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BatchComponent, BatchDetailComponent, BatchUpdateComponent, BatchDeleteDialogComponent, BatchDeletePopupComponent],
    entryComponents: [BatchComponent, BatchUpdateComponent, BatchDeleteDialogComponent, BatchDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TgUpload1BatchModule {}
