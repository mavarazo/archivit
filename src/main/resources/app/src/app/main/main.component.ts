import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from './user';
import { UserService } from './user.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  users: User[];

  constructor(private router: Router, private userService: UserService) { }

  getUsers() {
    this.userService.findAll().subscribe(data => {
      this.users = data;
    });
  }

  ngOnInit(): void {
    this.router.events.subscribe(value => {
      this.getUsers();
    });
  }

}
