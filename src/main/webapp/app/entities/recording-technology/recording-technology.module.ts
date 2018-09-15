import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TgUpload1SharedModule } from 'app/shared';
import {
    RecordingTechnologyComponent,
    RecordingTechnologyDetailComponent,
    RecordingTechnologyUpdateComponent,
    RecordingTechnologyDeletePopupComponent,
    RecordingTechnologyDeleteDialogComponent,
    recordingTechnologyRoute,
    recordingTechnologyPopupRoute
} from './';

const ENTITY_STATES = [...recordingTechnologyRoute, ...recordingTechnologyPopupRoute];

@NgModule({
    imports: [TgUpload1SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RecordingTechnologyComponent,
        RecordingTechnologyDetailComponent,
        RecordingTechnologyUpdateComponent,
        RecordingTechnologyDeleteDialogComponent,
        RecordingTechnologyDeletePopupComponent
    ],
    entryComponents: [
        RecordingTechnologyComponent,
        RecordingTechnologyUpdateComponent,
        RecordingTechnologyDeleteDialogComponent,
        RecordingTechnologyDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TgUpload1RecordingTechnologyModule {}
