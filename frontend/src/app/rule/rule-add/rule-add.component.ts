import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { RuleService } from '../rule.service';

@Component({
  selector: 'app-rule-add',
  templateUrl: './rule-add.component.html',
  styleUrls: ['./rule-add.component.scss'],
})
export class RuleAddComponent implements OnInit {
  form: FormGroup;

  constructor(
    private ruleService: RuleService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit() {
    this.form = this.formBuilder.group({
      name: [null, Validators.required],
      targetPath: [null, Validators.required],
    });
  }

  onSubmit() {
    if (this.form.valid) {
      this.ruleService.add(this.form.value).subscribe((rule) => {
        this.router.navigate(['rule', rule.id]);
      });
    }
  }
}
