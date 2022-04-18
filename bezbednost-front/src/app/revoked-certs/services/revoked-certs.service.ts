import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CertificateDTO } from 'src/app/model/certificate';

@Injectable({
  providedIn: 'root',
})
export class RevokedCertsService {
  constructor(private http: HttpClient) {}

  getAllCertificate(): Observable<CertificateDTO[]> {
    return this.http.get<CertificateDTO[]>(
      '/api/key-store/get-all-revoked-certificates'
    );
  }
}
