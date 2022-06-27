import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DeviceDTO } from 'src/app/model/device';

@Injectable({
  providedIn: 'root',
})
export class DevicesService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<DeviceDTO[]> {
    return this.http.get<DeviceDTO[]>('/api/devices', {});
  }
}
