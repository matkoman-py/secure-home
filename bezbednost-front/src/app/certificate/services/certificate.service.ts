import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from '@angular/common/http';
import { CertificateDTO } from "src/app/model/certificate";

@Injectable({
  providedIn: 'root',
})
export class CertificateService {
  constructor(private http: HttpClient) {}

  generateRoot(): Observable<String> {
    return this.http.get<String>('/api/key-store/generate-root');
  }

  getAllCertificate(): Observable<CertificateDTO[]> {
    return this.http.get<CertificateDTO[]>('/api/key-store/get-all-certificates');
  }
}