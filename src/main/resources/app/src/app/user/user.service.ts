import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = 'http://localhost:8080/api/users/';

  constructor(private http: HttpClient) {}

  getUsers(): Observable<any> {
    let result = this.http.get(`${this.url}`);
    return result;
  }
}
