import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RuleService } from '../rule.service';

@Component({
  selector: 'app-rule-add',
  templateUrl: './rule-add.component.html',
  styleUrls: ['./rule-add.component.scss'],
})
export class RuleAddComponent implements OnInit {
  addForm: FormGroup;

  constructor(
    private ruleService: RuleService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit() {
    this.addForm = this.formBuilder.group({
      name: [null, Validators.required],
      targetPath: [null, Validators.required],
    });
  }

  onSubmit() {
    this.ruleService.add(this.addForm.value).subscribe((rule) => {
      this.router.navigate(['rule', rule.id]);
    });
  }
}
