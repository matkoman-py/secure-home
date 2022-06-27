import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DeviceAlarm } from 'src/app/model/deviceAlarm';

@Injectable({
  providedIn: 'root',
})
export class DeviceAlarmsService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<DeviceAlarm[]> {
    return this.http.get<DeviceAlarm[]>('/api/devices/alarms', {});
  }
}
