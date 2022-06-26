import { Component, OnInit } from '@angular/core';
import { DeviceAlarm } from '../model/DeviceAlarm';
import { AlarmsDeviceService } from './services/alarms-device.service';

@Component({
  selector: 'app-alarms-device',
  templateUrl: './alarms-device.component.html',
  styleUrls: ['./alarms-device.component.css']
})
export class AlarmsDeviceComponent implements OnInit {

  alarms: DeviceAlarm[] = [];
  constructor(private alarmsDeviceService: AlarmsDeviceService) { }

  ngOnInit(): void {
    this.getAll();
  }

  getAll() {
    this.alarmsDeviceService.getAll().subscribe(res => {
      this.alarms = res;
      console.log(res);
    })
  }
}
