import { NgModule } from '@angular/core';

import { TgUpload1SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [TgUpload1SharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [TgUpload1SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class TgUpload1SharedCommonModule {}
