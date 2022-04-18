import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from '@angular/common/http';
import { CertificateDTO, ExtensionsDTO } from "src/app/model/certificate";

@Injectable({
  providedIn: 'root',
})
export class CertificateInfoService {
  constructor(private http: HttpClient) {}


  getCertificate(alias: string, file:string): Observable<CertificateDTO> {
    return this.http.get<CertificateDTO>('/api/key-store/get-certificate/'+ alias+"/"+file);
  }

  getExtension(alias: string, file: string): Observable<ExtensionsDTO> {
    return this.http.get<ExtensionsDTO>('/api/key-store/get-extensions/'+ alias+"/"+file);
  }
}