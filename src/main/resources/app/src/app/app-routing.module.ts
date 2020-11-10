import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FileComponent } from './file/file.component';

const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'file', children: [
    { path: '', component: FileComponent},
    { path: ':fileId', component: FileComponent}
  ] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
