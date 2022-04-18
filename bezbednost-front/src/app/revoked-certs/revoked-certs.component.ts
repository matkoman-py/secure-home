import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CertificateDTO } from '../model/certificate';
import { RevokedCertsService } from './services/revoked-certs.service';

@Component({
  selector: 'app-revoked-certs',
  templateUrl: './revoked-certs.component.html',
  styleUrls: ['./revoked-certs.component.css'],
})
export class RevokedCertsComponent implements OnInit {
  certificates: CertificateDTO[] = [];

  constructor(
    private certificateService: RevokedCertsService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.certificateService.getAllCertificate().subscribe((res) => {
      console.log(res);
      this.certificates = res;
    });
  }
  go(e: string): void {
    this.router.navigateByUrl('/certificate/revoke/'+e);

  }
}
