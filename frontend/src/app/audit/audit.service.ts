import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Audit } from './audit';

@Injectable({
  providedIn: 'root',
})
export class AuditService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Audit[]> {
    return this.http.get<Audit[]>('http://localhost:8080/api/audit/');
  }

  get(id: number): Observable<Audit> {
    return this.http.get<Audit>(`http://localhost:8080/api/audit/${id}`);
  }
}
