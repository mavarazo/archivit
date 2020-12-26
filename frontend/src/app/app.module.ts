import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuditListComponent } from './audit/audit-list/audit-list.component';
import { AuditDetailComponent } from './audit/audit-detail/audit-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    AuditListComponent,
    AuditDetailComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
