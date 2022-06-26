import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CsrDTO } from 'src/app/model/csr';
import { LogDTO } from 'src/app/model/log';

@Injectable({
  providedIn: 'root'
})
export class LogsService {

  constructor(private http: HttpClient) { }

  getAllLogs(before: string, after: string, level: string, message: string, app: string, user: string): Observable<LogDTO[]> {
    let params = new HttpParams();
	//String level, String message, String app, String user

    return this.http.get<LogDTO[]>('/api/logs/getAll',{
      params: {
        before: before,
        after:after,
        level:level,
        message:message,
        app:app,
        user:user
      }
    });
  }
}
