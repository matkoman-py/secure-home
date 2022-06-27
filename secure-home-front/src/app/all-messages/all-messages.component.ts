import { Component, OnInit } from '@angular/core';
import { MessageDTO } from '../model/MessageDTO';
import { AllMessagesService } from './service/all-messages.service';

@Component({
  selector: 'app-all-messages',
  templateUrl: './all-messages.component.html',
  styleUrls: ['./all-messages.component.css']
})
export class AllMessagesComponent implements OnInit {
  messages: MessageDTO[] = [];
  constructor(private allMessagesService: AllMessagesService) { }

  ngOnInit(): void {
    this.getAll();
  }

  getAll() {
    this.allMessagesService.getAll().subscribe(res => {
      this.messages = res;
    })
  }
}
