import { componentFactoryName } from '@angular/compiler';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuditDetailComponent } from './audit/audit-detail/audit-detail.component';
import { AuditListComponent } from './audit/audit-list/audit-list.component';
import { RuleListComponent } from './rule/rule-list/rule-list.component';

const routes: Routes = [
  { path: '', component: AuditListComponent },
  { path: 'audit/:id', component: AuditDetailComponent },
  { path: 'rule', component: RuleListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
