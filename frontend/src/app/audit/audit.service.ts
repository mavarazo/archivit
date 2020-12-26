import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuditService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<any> {
    return this.http.get('http://localhost:8080/api/audit/');
  }

  get(id: number): Observable<any> {
    return this.http.get(`http://localhost:8080/api/audit/${id}`);
  }
}
