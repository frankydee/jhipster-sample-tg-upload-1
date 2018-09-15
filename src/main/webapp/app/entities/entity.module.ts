import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TgUpload1RecordingTechnologyModule } from './recording-technology/recording-technology.module';
import { TgUpload1FileByTechnologyModule } from './file-by-technology/file-by-technology.module';
import { TgUpload1FileTypeModule } from './file-type/file-type.module';
import { TgUpload1BatchModule } from './batch/batch.module';
import { TgUpload1UploadedFileModule } from './uploaded-file/uploaded-file.module';
import { TgUpload1InvalidationRuleModule } from './invalidation-rule/invalidation-rule.module';
import { TgUpload1InvalidationCodeModule } from './invalidation-code/invalidation-code.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        TgUpload1RecordingTechnologyModule,
        TgUpload1FileByTechnologyModule,
        TgUpload1FileTypeModule,
        TgUpload1BatchModule,
        TgUpload1UploadedFileModule,
        TgUpload1InvalidationRuleModule,
        TgUpload1InvalidationCodeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TgUpload1EntityModule {}
