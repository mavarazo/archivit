import { Component, OnInit } from '@angular/core';
import { User } from '../service/user.model';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  user: User;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.get();
  }

  get(): void {
    this.userService.findById(1).subscribe((data) => (this.user = data));
  }
}
