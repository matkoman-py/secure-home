import { Component, OnInit } from '@angular/core';
import { CertificateService } from '../certificate/services/certificate.service';
import { CertificateDTO } from '../model/certificate';
import { CsrDTO, KeyUsages } from '../model/csr';
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
  extenstionsModal = false;
  basicConstraintIsCa = false;
  subjectName: string = '';
  selectedIssuerName: string = '';
  basicConstraint: string = '';
  aki: string = '';
  ski: string = '';
  keyUsages: KeyUsages[] = [
    { name: 'Crl sign', code: 2 },
    { name: 'Data encipherment', code: 16 },
    { name: 'Decipher only', code: 32768 },
    { name: 'Digital signature', code: 128 },
    { name: 'Encipher only', code: 1 },
    { name: 'Key agreement', code: 8 },
    { name: 'Key cert sign', code: 4 },
    { name: 'Key encipherment', code: 32 },
    { name: 'Non repudiation', code: 64 },
  ];
  keyUsagesSelected: KeyUsages[] = [];
  extendedKeyUsages: KeyUsages[] = [
    { name: 'Any extended key usage', code: 1 },
    { name: 'Code signing', code: 2 },
    { name: 'Email protection', code: 3 },
    { name: 'Ip security end system', code: 4 },
    { name: 'Ip security tunnel termination', code: 5 },
    { name: 'Ip security user', code: 6 },
    { name: 'OCSP signing', code: 7 },
    { name: 'Smartcard logon', code: 8 },
    { name: 'Timestamping', code: 9 },
    { name: 'TLS web client authentication', code: 10 },
    { name: 'TLS web server authentication', code: 11 },
  ];
  extendedKeyUsagesSelected: KeyUsages[] = [];
  ipAddress: string = '';
  uri: string = '';
  upn: string = '';
  directoryName: string = '';
  dnsName: string = '';
  rfc822: string = '';

  criticalSubjectAlternativeName = '';
  criticalExtendedKeyUsage = '';
  criticalKeyUsage = '';
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
    this.extenstionsModal = true;
    this.selectedIssuerName = alias;
  }

  signCSR() {
    this.csrService
      .signCsr(this.selectedIssuerName, this.subjectName)
      .subscribe((res) => {
        console.log(res);
        this.displayCertificateTableDialog = false;

        this.getAllCertificates();
      });
  }
}
