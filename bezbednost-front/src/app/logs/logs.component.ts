import { Component, OnInit } from '@angular/core';
import { LogDTO } from '../model/log';
import { LogsService } from './service/logs.service';

@Component({
  selector: 'app-logs',
  templateUrl: './logs.component.html',
  styleUrls: ['./logs.component.css']
})
export class LogsComponent implements OnInit {

  constructor(private logsService: LogsService) { }

  logs: LogDTO[] = []

  convertDate(date: string) {
    return new Date(date).toLocaleString()
  }

  ngOnInit(): void {
    this.logsService.getAllLogs().subscribe(res => {
      this.logs = res;
      console.log( new Date(res[0]["date"].toString()).toLocaleString());
    })
  }

}
