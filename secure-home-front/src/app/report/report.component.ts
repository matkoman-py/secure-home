import { Component, OnInit } from '@angular/core';
import { ReportDTO } from '../model/report';
import { ReportService } from './service/report.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css'],
})
export class ReportComponent implements OnInit {
  devices: ReportDTO = {};
  before: Date = new Date('2023-01-01');
  after: Date = new Date('1970-01-01');

  allAlarms: number = 0;

  constructor(private service: ReportService) {}

  ngOnInit(): void {
    this.getReport();
  }

  convertDateTime(newDate: Date) {
    if (!newDate) {
      return '';
    }
    let yourDate = newDate;
    const offset = yourDate.getTimezoneOffset();
    yourDate = new Date(yourDate.getTime() - offset * 60 * 1000);
    let dateTime = yourDate.toISOString().split('T');
    let date = dateTime[0];
    let time = dateTime[1].split('Z')[0].split('.')[0];
    console.log(date + ' ' + time);
    return date + ' ' + time;
  }

  getReport() {
    this.service
      .getReport(
        this.convertDateTime(this.before),
        this.convertDateTime(this.after)
      )
      .subscribe((res) => {
        this.devices = res;
        console.log(res);
      });
  }
}
