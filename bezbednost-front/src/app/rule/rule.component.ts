import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { throwIfEmpty } from 'rxjs';
import { Device } from '../model/Device';
import { RulePreview } from '../model/Rule';
import { RuleService } from './service/rule.service';

@Component({
  selector: 'app-rule',
  templateUrl: './rule.component.html',
  styleUrls: ['./rule.component.css'],
  providers: [MessageService],

})
export class RuleComponent implements OnInit {


  rules: RulePreview[] = []

  devices: Device[] = [];

  device: Device = {id:0, pathFile:""};

  lowerValue: number = 0;
  upperValue: number = 0;
  constructor(private ruleService: RuleService, private messageService: MessageService) { }


  getAllRules(): void {
    this.ruleService.getAllRules().subscribe(res => {
      this.rules = res;
      console.log(res);
    })
  }

  getAllDevices(): void {
    this.ruleService.getAllDevices().subscribe(res => {
      this.devices = res;
    })
  }
  ngOnInit(): void {
    this.getAllRules();
    this.getAllDevices();
  }

  postRule(): void {

    if(this.device == null || this.device == undefined) {
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Error',
        detail: 'No device has been selected',
      });
      return;
    }
    if(this.device.id == 0) {
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Error',
        detail: 'No device has been selected',
      });
      return;
    }

    if(this.lowerValue == null ||  this.upperValue == null || this.lowerValue == undefined || this.upperValue == undefined) {
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Error',
        detail: 'Values are not defined',
      });
      return;
    }
    if(this.lowerValue >= this.upperValue) {
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Error',
        detail: 'Lower value must be lower than upper value',
      });
      return;
    }
    console.log(this.device.id)
    this.ruleService.postRule({deviceId:this.device.id,lowerValue:this.lowerValue,upperValue:this.upperValue}).subscribe(res => {
      this.messageService.add({
        key: 'tc',
        severity: 'success',
        summary: 'Success',
        detail: res.toString(),
      });
      this.getAllRules();
    })
    
  }

}
