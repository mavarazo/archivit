import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FileService {
  private API_URL = 'http://localhost:8080/api/files/?userId=1';

  constructor(private router: Router, private httpClient: HttpClient) {}

  findAll(): Observable<any> {
    return this.httpClient.get(`${this.API_URL}`);
  }
}
