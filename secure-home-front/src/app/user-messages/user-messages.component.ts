import {
  Component,
  OnInit
} from '@angular/core';
import {
  MessageDTO
} from '../model/MessageDTO';
import {
  UserMessagesService
} from './services/user-messages.service';

@Component({
  selector: 'app-user-messages',
  templateUrl: './user-messages.component.html',
  styleUrls: ['./user-messages.component.css']
})
export class UserMessagesComponent implements OnInit {

  constructor(private userMessagesService: UserMessagesService) {}

  messages: MessageDTO[] = []

  before: Date = new Date("2023-01-01")
  after: Date = new Date("1970-01-01")
  message: String = ""
  pathToFile: String = ""

  convertDateTime(newDate: Date) {
    if (!newDate) {
      return ""
    }
    let yourDate = newDate
    const offset = yourDate.getTimezoneOffset()
    yourDate = new Date(yourDate.getTime() - (offset * 60 * 1000))
    let dateTime = yourDate.toISOString().split("T")
    let date = dateTime[0]
    let time = dateTime[1].split("Z")[0].split(".")[0]
    console.log(date + " " + time)
    return date + " " + time
  }

  filter() {
    this.userMessagesService.getAll(this.convertDateTime(this.before), this.convertDateTime(this.after), this.message.trim(),
      this.pathToFile.trim(), ).subscribe(res => {
      this.messages = res;
    })

  }

  convertDate(date: string) {
    return new Date(date).toLocaleString()
  }

  ngOnInit(): void {
    this.userMessagesService.getAll("", "", "", "").subscribe(res => {
      this.messages = res;
    })
  }
}
