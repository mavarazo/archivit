import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuditListComponent } from './audit/audit-list/audit-list.component';

const routes: Routes = [{ path: '', component: AuditListComponent }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
