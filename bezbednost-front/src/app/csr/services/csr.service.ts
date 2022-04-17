import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CsrDTO } from 'src/app/model/csr';

@Injectable({
  providedIn: 'root',
})
export class CsrService {
  constructor(private http: HttpClient) {}

  getAllCsrs(): Observable<CsrDTO[]> {
    return this.http.get<CsrDTO[]>('/api/key-store/get-all-csrs');
  }

  signCsr(issuerAlias: string, subjectAlias: string): Observable<String> {
    return this.http.post<String>(
      `/api/test/sign-csr/${issuerAlias}/${subjectAlias}`,
      { extensions: null }
    );
  }
}
