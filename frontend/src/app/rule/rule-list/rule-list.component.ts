import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { Rule } from '../rule';
import { RuleService } from '../rule.service';
import { faPlus, faPen, faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-rule-list',
  templateUrl: './rule-list.component.html',
  styleUrls: ['./rule-list.component.scss'],
})
export class RuleListComponent implements OnInit {
  rules: Rule[];

  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject<any>();

  faPlus = faPlus;
  faPen = faPen;
  faTrash = faTrash;

  constructor(private ruleService: RuleService) {}

  ngOnInit(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 50,
    };

    this.ruleService.getAll().subscribe(
      (data) => {
        this.rules = data;
        this.dtTrigger.next();
      },
      (error) => console.log(error)
    );
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }
}
