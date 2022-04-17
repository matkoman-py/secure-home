import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { csrCreateDTO } from '../model/csrCreate';
import { GenerateCsrService } from './services/generate-csr.service';

@Component({
  selector: 'app-generate-csr',
  templateUrl: './generate-csr.component.html',
  styleUrls: ['./generate-csr.component.css'],
  providers: [MessageService],
})
export class GenerateCsrComponent implements OnInit {

  csrInfo: csrCreateDTO = {};
  kurac: string = "";

  constructor(private generateCsrService: GenerateCsrService,
              private messageService: MessageService) { }

  ngOnInit(): void {
  }

  generateCsr() {
    this.generateCsrService.generateCsr(this.csrInfo).subscribe(res => {
      this.messageService.add({
        key: 'tc',
        severity: 'success',
        summary: 'Success',
        detail: 'CSR succesfully created',
      });
    }, err =>{
      this.messageService.add({
        key: 'tc',
        severity: 'success',
        summary: 'Success',
        detail: 'CSR succesfully created',
      });
    })
  }

  isInfoValid() {
    if (this.csrInfo.city == null) return false;
    if (this.csrInfo.country == null) return false;
    if (this.csrInfo.domainName == null) return false;
    if (this.csrInfo.email == null) return false;
    if (this.csrInfo.organizationName == null) return false;
    if (this.csrInfo.organizationUnit == null) return false;
    if (this.csrInfo.reason == null) return false;
    if (this.csrInfo.state == null) return false;

    return true;
  }
}
