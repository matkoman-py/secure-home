import { Component, OnInit } from '@angular/core';
import { DeviceDTO } from '../model/device';
import { DevicesService } from './service/devices.service';

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.css'],
})
export class DevicesComponent implements OnInit {
  devices: DeviceDTO[] = [];
  constructor(private service: DevicesService) {}

  ngOnInit(): void {
    this.getAll();
  }

  getAll() {
    this.service.getAll().subscribe((res) => {
      this.devices = res;
      console.log(res);
    });
  }
}
