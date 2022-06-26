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

  before: Date = new Date("2023-01-01")
  after: Date = new Date("1970-01-01")
  level: String = ""
  message: String = ""
  app: String = ""
  user: String = ""

  convertDateTime(newDate: Date) {
    if(!newDate) {
      return ""
    }
    let yourDate = newDate
    const offset = yourDate.getTimezoneOffset()
    yourDate = new Date(yourDate.getTime() - (offset*60*1000))
    let dateTime = yourDate.toISOString().split("T")
    let date = dateTime[0]
    let time = dateTime[1].split("Z")[0].split(".")[0]
    console.log(date + " " + time)
    return date + " " + time
  }

  filter() {
    this.logsService.getAllLogs(this.convertDateTime(this.before), this.convertDateTime(this.after),this.level.trim(),this.message.trim(),this.app.trim(),this.user.trim()).subscribe(res => {
      this.logs = res;
    })

  }

  convertDate(date: string) {
    return new Date(date).toLocaleString()
  }

  ngOnInit(): void {
    this.logsService.getAllLogs("","","","","","").subscribe(res => {
      this.logs = res;
      console.log( new Date(res[0]["date"].toString()).toLocaleString());
    })
  }

}
