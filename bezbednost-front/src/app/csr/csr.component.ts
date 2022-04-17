import { Component, OnInit } from '@angular/core';
import { CertificateService } from '../certificate/services/certificate.service';
import { CertificateDTO } from '../model/certificate';
import { CsrDTO } from '../model/csr';
import { CsrService } from './services/csr.service';

@Component({
  selector: 'app-csr',
  templateUrl: './csr.component.html',
  styleUrls: ['./csr.component.css'],
})
export class CsrComponent implements OnInit {
  csrs: CsrDTO[] = [];
  certificates: CertificateDTO[] = [];
  displayCertificateTableDialog = false;
  subjectName: string = '';

  constructor(
    private csrService: CsrService,
    private certificateService: CertificateService
  ) {}

  ngOnInit(): void {
    this.getAllCsrs();
  }

  openDialogForIssuer(alias: string): void {
    this.displayCertificateTableDialog = true;
    this.subjectName = alias;
  }

  getAllCsrs(): void {
    this.csrService.getAllCsrs().subscribe((res) => {
      this.csrs = res;
      this.getAllCertificates();
    });
  }

  getAllCertificates() {
    this.certificateService.getAllCertificate().subscribe((res) => {
      console.log(res);
      this.certificates = res;
    });
  }
  selectedIssuer(alias: string): void {
    //ovo ce ici u drugu funk
    this.csrService.signCsr(alias, this.subjectName).subscribe((res) => {
      console.log(res);
      this.displayCertificateTableDialog = false;

      this.getAllCertificates();
    });
  }
}
