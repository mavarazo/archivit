import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Rule } from '../rule';
import { RuleService } from '../rule.service';

@Component({
  selector: 'app-rule-detail',
  templateUrl: './rule-detail.component.html',
  styleUrls: ['./rule-detail.component.scss'],
})
export class RuleDetailComponent implements OnInit {
  rule: Rule;

  constructor(
    private ruleService: RuleService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const idFromRoute = this.route.snapshot.paramMap.get('id');
    this.ruleService.get(+idFromRoute).subscribe(
      (data) => {
        this.rule = data;
      },
      (error) => console.log(error)
    );
  }
}
