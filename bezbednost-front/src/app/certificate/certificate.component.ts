import { Component, OnInit } from '@angular/core';
import { CertificateService } from './services/certificate.service';
import { CertificateDTO } from '../model/certificate';
import { Router } from '@angular/router';
import {MessageService} from 'primeng/api';



@Component({
  selector: 'app-certificate',
  templateUrl: './certificate.component.html',
  styleUrls: ['./certificate.component.css'],
  providers: [MessageService],

})
export class CertificateComponent implements OnInit {
  
  certificates: CertificateDTO[] = [];
  display: boolean = false;
  selectedAlias: string = "";
  autoResize: boolean = true;
  reason: string = ""


  constructor(private certificateService: CertificateService, private router: Router, private messageService: MessageService) { }

  ngOnInit(): void {
    
    this.getAllCertificates();
  }

  getAllCertificates(): void {
    this.certificateService.getAllCertificate().subscribe(res => {
      console.log(res);
      this.certificates = res;
    })
  }

  go(e: string): void {
    this.router.navigateByUrl('/certificate/'+e);
  }

  show(e: string): void {
    this.selectedAlias = e;
    this.display = true;
  }

  delete(e: string): void {
    this.certificateService.deleteCertificate(e, this.reason).subscribe(res => {
      this.messageService.add({key: 'tc', severity:'success', summary: 'Successfully revoked', detail: 'Certificate (and all children of said certificate) have been successfully revoked!'});
      this.selectedAlias = "";
      this.display = false;
      this.getAllCertificates();
    })
  }



}
