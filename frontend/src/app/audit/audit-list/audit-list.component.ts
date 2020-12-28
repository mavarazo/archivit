import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';

import { Audit } from '../audit';
import { AuditService } from '../audit.service';

@Component({
  selector: 'app-audit-list',
  templateUrl: './audit-list.component.html',
  styleUrls: ['./audit-list.component.scss'],
})
export class AuditListComponent implements OnInit {
  audits: Audit[];

  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject<any>();

  constructor(private auditService: AuditService) {}

  ngOnInit(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 50,
    };

    this.auditService.getAll().subscribe(
      (data) => {
        this.audits = data;
        this.dtTrigger.next();
      },
      (error) => console.log(error)
    );
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }
}
