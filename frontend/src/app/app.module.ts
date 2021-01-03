import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { DataTablesModule } from 'angular-datatables';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuditListComponent } from './audit/audit-list/audit-list.component';
import { AuditDetailComponent } from './audit/audit-detail/audit-detail.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RuleListComponent } from './rule/rule-list/rule-list.component';
import { RuleDetailComponent } from './rule/rule-detail/rule-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    AuditListComponent,
    AuditDetailComponent,
    RuleListComponent,
    RuleDetailComponent,
    
  ],
  imports: [
    CommonModule,
    BrowserModule,
    HttpClientModule,
    DataTablesModule,
    AppRoutingModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
