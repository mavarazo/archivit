import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Audit } from '../audit';
import { AuditService } from '../audit.service';

@Component({
  selector: 'app-audit-detail',
  templateUrl: './audit-detail.component.html',
  styleUrls: ['./audit-detail.component.scss'],
})
export class AuditDetailComponent implements OnInit {
  audit: Audit;

  constructor(
    private auditService: AuditService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const idFromRoute = this.route.snapshot.paramMap.get('id');
    this.auditService.get(+idFromRoute).subscribe(
      (data) => {
        this.audit = data;
      },
      (error) => console.log(error)
    );
  }
}
