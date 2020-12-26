import { Component, OnInit } from '@angular/core';
import { Audit } from '../audit';
import { AuditService } from '../audit.service';

@Component({
  selector: 'app-audit-list',
  templateUrl: './audit-list.component.html',
  styleUrls: ['./audit-list.component.scss'],
})
export class AuditListComponent implements OnInit {
  audits: Audit[];

  constructor(private auditService: AuditService) {}

  ngOnInit(): void {
    this.auditService.getAll().subscribe(
      (data) => {
        this.audits = data;
      },
      (error) => console.log(error)
    );
  }
}
