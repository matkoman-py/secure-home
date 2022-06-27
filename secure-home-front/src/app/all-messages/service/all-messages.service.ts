import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MessageDTO } from 'src/app/model/MessageDTO';

@Injectable({
  providedIn: 'root'
})
export class AllMessagesService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<MessageDTO[]> {
    return this.http.get<MessageDTO[]>('/api/messages', {});
  }
}
