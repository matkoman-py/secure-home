import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MessageDTO } from 'src/app/model/MessageDTO';

@Injectable({
  providedIn: 'root'
})
export class UserMessagesService {

  constructor(private http: HttpClient) { }

  getAll(before: string, after: string, message: string, pathToFile: string): Observable<MessageDTO[]> {
    let params = new HttpParams();

    return this.http.get<MessageDTO[]>('/api/devices/messages',{
      params: {
        dateBefore: before,
        dateAfter:after,
        message:message,
        pathToFile:pathToFile,
      }
    });
  }
}
