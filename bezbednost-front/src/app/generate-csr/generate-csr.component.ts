import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { csrCreateDTO } from '../model/csrCreate';
import { KeySizeOptions } from '../model/KeySizeOptions';
import { GenerateCsrService } from './services/generate-csr.service';

@Component({
  selector: 'app-generate-csr',
  templateUrl: './generate-csr.component.html',
  styleUrls: ['./generate-csr.component.css'],
  providers: [MessageService],
})
export class GenerateCsrComponent implements OnInit {

  csrInfo: csrCreateDTO = {};
  sizes: KeySizeOptions[] = [
    {name: 2048, code: 2048},
    {name: 1024, code: 1024},
    {name: 512, code: 512},
  ];

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

    this.csrInfo = {}
  }

  isInfoValid() {
    if (this.csrInfo.city == null) return false;
    if (this.csrInfo.country == null) return false;
    if (this.csrInfo.domainName == null) return false;
    if (this.csrInfo.email == null) return false;
    if (this.csrInfo.organizationName == null) return false;
    if (this.csrInfo.organizationUnit == null) return false;
    if (this.csrInfo.state == null) return false;

    return true;
  }
}
