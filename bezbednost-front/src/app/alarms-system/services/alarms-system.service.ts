import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SystemAlarm } from 'src/app/model/SystemAlarm';

@Injectable({
  providedIn: 'root'
})
export class AlarmsSystemService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<SystemAlarm[]> {
    return this.http.get<SystemAlarm[]>('/api/alarms/system', {});
  }
}
