import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FileService {

  private baseUrl = "http://localhost:8080/api/file";

  constructor(private http: HttpClient) {}

  findAll(): Observable<any> {
    const url = `${this.baseUrl}/?userId=1`;
    return this.http.get(url);
  }

  findById(id: number): Observable<any> {
    const url = `${this.baseUrl}/${id}/?userId=1`;
    return this.http.get(url);
  }

  findAllByTagsIsEmpty(): Observable<any> {
    const url = `${this.baseUrl}/untagged/?userId=1`;
    return this.http.get(url);
  }
}
