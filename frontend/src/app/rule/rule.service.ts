import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Rule } from './rule';

@Injectable({
  providedIn: 'root',
})
export class RuleService {
  constructor(private http: HttpClient) {}

  private url = 'http://localhost:8080/api/rule';

  getAll(): Observable<Rule[]> {
    return this.http.get<Rule[]>(`${this.url}/`);
  }

  get(id: number): Observable<Rule> {
    return this.http.get<Rule>(`${this.url}/${id}`);
  }

  add(rule: Rule): Observable<Rule> {
    return this.http.post<Rule>(`${this.url}/`, rule);
  }

  change(rule: Rule, id: number): Observable<Rule> {
    return this.http.put<Rule>(`${this.url}/${id}`, rule);
  }

  delete(id: number): Observable<Rule> {
    return this.http.delete<Rule>(`${this.url}/${id}`);
  }
}
