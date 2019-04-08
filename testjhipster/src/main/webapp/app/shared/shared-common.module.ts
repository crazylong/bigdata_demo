import { NgModule } from '@angular/core';

import { TestjhipsterSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [TestjhipsterSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [TestjhipsterSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class TestjhipsterSharedCommonModule {}
