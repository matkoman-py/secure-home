import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from '@angular/common/http';
import { csrCreateDTO } from "src/app/model/csrCreate";

@Injectable({
  providedIn: 'root',
})
export class GenerateCsrService {
  constructor(private http: HttpClient) {}

  generateCsr(csrInfo: csrCreateDTO): Observable<String> {
    return this.http.post<String>('/api/test', csrInfo);
  }
}