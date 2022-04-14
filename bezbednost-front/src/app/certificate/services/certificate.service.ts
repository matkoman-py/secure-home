import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class CertificateService {
  constructor(private http: HttpClient) {}

  generateRoot(): Observable<String> {
    return this.http.get<String>('/api/key-store/generate-root');
  }
}