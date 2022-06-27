import { Component, OnInit } from '@angular/core';
import { DeviceAlarm } from '../model/deviceAlarm';
import { DeviceAlarmsService } from './service/device-alarms.service';

@Component({
  selector: 'app-device-alarms',
  templateUrl: './device-alarms.component.html',
  styleUrls: ['./device-alarms.component.css'],
})
export class DeviceAlarmsComponent implements OnInit {
  alarms: DeviceAlarm[] = [];
  constructor(private alarmsDeviceService: DeviceAlarmsService) {}

  ngOnInit(): void {
    this.getAll();
  }

  getAll() {
    this.alarmsDeviceService.getAll().subscribe((res) => {
      this.alarms = res;
      console.log(res);
    });
  }
}
