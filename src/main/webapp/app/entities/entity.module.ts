import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TgUpload1RecordingTechnologyModule } from './recording-technology/recording-technology.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        TgUpload1RecordingTechnologyModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TgUpload1EntityModule {}
