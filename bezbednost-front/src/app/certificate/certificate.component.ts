import { Component, OnInit } from '@angular/core';
import { CertificateService } from './services/certificate.service';
import { CertificateDTO } from '../model/certificate';

@Component({
  selector: 'app-certificate',
  templateUrl: './certificate.component.html',
  styleUrls: ['./certificate.component.css']
})
export class CertificateComponent implements OnInit {
  

  certificates: CertificateDTO[] = [];
  constructor(private certificateService: CertificateService) { }

  ngOnInit(): void {
    
    this.certificateService.getAllCertificate().subscribe(res => {
      console.log(res);
      this.certificates = res;
    })
  }



}
