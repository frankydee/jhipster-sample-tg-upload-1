import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TgUpload1SharedModule } from 'app/shared';
import {
    InvalidationRuleComponent,
    InvalidationRuleDetailComponent,
    InvalidationRuleUpdateComponent,
    InvalidationRuleDeletePopupComponent,
    InvalidationRuleDeleteDialogComponent,
    invalidationRuleRoute,
    invalidationRulePopupRoute
} from './';

const ENTITY_STATES = [...invalidationRuleRoute, ...invalidationRulePopupRoute];

@NgModule({
    imports: [TgUpload1SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InvalidationRuleComponent,
        InvalidationRuleDetailComponent,
        InvalidationRuleUpdateComponent,
        InvalidationRuleDeleteDialogComponent,
        InvalidationRuleDeletePopupComponent
    ],
    entryComponents: [
        InvalidationRuleComponent,
        InvalidationRuleUpdateComponent,
        InvalidationRuleDeleteDialogComponent,
        InvalidationRuleDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TgUpload1InvalidationRuleModule {}
