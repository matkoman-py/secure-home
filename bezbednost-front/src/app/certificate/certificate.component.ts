import { Component, OnInit } from '@angular/core';
import { CertificateService } from './services/certificate.service';
import { CertificateDTO } from '../model/certificate';
import { Router } from '@angular/router';


@Component({
  selector: 'app-certificate',
  templateUrl: './certificate.component.html',
  styleUrls: ['./certificate.component.css']
})
export class CertificateComponent implements OnInit {
  
  certificates: CertificateDTO[] = [];
  constructor(private certificateService: CertificateService, private router: Router) { }

  ngOnInit(): void {
    
    this.certificateService.getAllCertificate().subscribe(res => {
      console.log(res);
      this.certificates = res;
    })
  }

  go(e: string): void {
    this.router.navigateByUrl('/certificate/'+e);
  }



}
