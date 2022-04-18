import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CsrDTO } from 'src/app/model/csr';
import { ExtensionsDTO } from 'src/app/model/extensions';

@Injectable({
  providedIn: 'root',
})
export class CsrService {
  constructor(private http: HttpClient) {}

  getAllCsrs(): Observable<CsrDTO[]> {
    return this.http.get<CsrDTO[]>('/api/key-store/get-all-csrs');
  }

  signCsr(
    issuerAlias: string,
    subjectAlias: string,
    extensions: ExtensionsDTO
  ): Observable<any> {
    var extensionsDto = { extensions };

    return this.http.post(
      `/api/test/sign-csr/${issuerAlias}/${subjectAlias}`,
      extensionsDto,
      { responseType: 'text' }
    );
  }
}
