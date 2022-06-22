import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CsrDTO } from 'src/app/model/csr';
import { LogDTO } from 'src/app/model/log';

@Injectable({
  providedIn: 'root'
})
export class LogsService {

  constructor(private http: HttpClient) { }

  getAllLogs(): Observable<LogDTO[]> {
    return this.http.get<LogDTO[]>('/api/logs/getAll');
  }
}
