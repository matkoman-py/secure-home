import { Component, OnInit } from '@angular/core';
import { MessageService, PrimeNGConfig } from 'primeng/api';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { Message } from '@stomp/stompjs';
import { DevicesService } from '../devices/service/devices.service';
import { DeviceDTO } from '../model/device';
@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css'],
  providers: [MessageService],
})
export class NotificationsComponent implements OnInit {
  webSocketEndPoint: string = '/api/sba-websocket';
  devices: DeviceDTO[] = [];
  stompClient: any;

  role: string | null = '';
  constructor(
    private messageService: MessageService,
    private primengConfig: PrimeNGConfig,
    private deviceService: DevicesService
  ) {}

  connect() {
    console.log('Initialize WebSocket Connection');
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const _this = this;
    _this.stompClient.connect(
      {},
      function () {
        _this.stompClient.subscribe(
          '/topic/alarm',
          function (message: Message) {
            _this.onNewOrderReceived(message);
          }
        );
      },
      this.errorCallBack
    );
  }

  disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
    console.log('Disconnected');
  }

  errorCallBack(error: string) {
    console.log('errorCallBack -> ' + error);
    setTimeout(() => {
      this.connect();
    }, 5000);
  }

  onNewOrderReceived(message: Message) {
    if (sessionStorage.getItem('role') === 'ROLE_USER') {
      this.deviceService.getAll().subscribe((res) => {
        this.devices = res;
        const myArray = message.body.split(' ');
        if (this.devices.some((e) => e.pathToFile === myArray[3])) {
          this.messageService.add({
            key: 'alarm',
            severity: 'info',
            summary: 'Notification',
            detail: 'New alarm: ' + message.body,
          });
        }
      });
    }
  }

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    this.connect();
  }
}
