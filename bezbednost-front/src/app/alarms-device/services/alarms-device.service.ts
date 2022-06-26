import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DeviceAlarm } from 'src/app/model/DeviceAlarm';

@Injectable({
  providedIn: 'root'
})
export class AlarmsDeviceService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<DeviceAlarm[]> {
    return this.http.get<DeviceAlarm[]>('/api/alarms/devices', {});
  }
}
