import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private API_URL = 'http://localhost:8080/api/users/';

  constructor(private router: Router, private httpClient: HttpClient) { }

  findAll(): Observable<any> {
    return this.httpClient.get(`${this.API_URL}`);
  }
}
