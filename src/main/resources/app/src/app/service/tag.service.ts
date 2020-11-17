import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tag } from './tag.model';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  private baseUrl = "http://localhost:8080/api/tag";

  constructor(private http: HttpClient) {}

  findAll(): Observable<Tag[]> {
    const url = `${this.baseUrl}/?userId=1`;
    return this.http.get(url);
  }
}
