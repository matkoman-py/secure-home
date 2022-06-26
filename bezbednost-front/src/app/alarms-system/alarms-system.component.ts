import { Component, OnInit } from '@angular/core';
import { SystemAlarm } from '../model/SystemAlarm';
import { AlarmsSystemService } from './services/alarms-system.service';

@Component({
  selector: 'app-alarms-system',
  templateUrl: './alarms-system.component.html',
  styleUrls: ['./alarms-system.component.css']
})
export class AlarmsSystemComponent implements OnInit {

  alarms: SystemAlarm[] = [];
  constructor(private alarmsSystemService: AlarmsSystemService) { }

  ngOnInit(): void {
    this.getAll();
  }

  getAll() {
    this.alarmsSystemService.getAll().subscribe(res => {
      this.alarms = res;
      console.log(res);
    })
  }
}
