import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = "http://localhost:8080/api/user";

  constructor(private http: HttpClient) {}

  findAll(): Observable<any> {
    const url = `${this.baseUrl}/`;
    return this.http.get(url);
  }

  findById(id: number): Observable<any> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get(url);
  }
}
