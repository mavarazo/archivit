import { Component, OnInit } from '@angular/core';
import { User } from './service/user.model';

import { UserService } from './service/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'app';
  collapsed = true;

  users: User[];
  user: User;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.get();
  }

  get(): void {
    this.userService.findAll().subscribe(data => this.users = data)
    this.userService.findById(1).subscribe(data => this.user = data)
  }
}
