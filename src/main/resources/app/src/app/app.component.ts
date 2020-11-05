import { Component, OnInit} from '@angular/core';
import {User} from './user/user';
import {UserService} from './user/user.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'Archivit';
  users: User[];

  constructor(private router: Router, private userService: UserService) {}

  getUsers() {
    this.userService.getUsers().subscribe(data => { this.users = data; });
  }

  ngOnInit(): void {
    this.router.events.subscribe(value => {
      this.getUsers();
    });
  }
}
