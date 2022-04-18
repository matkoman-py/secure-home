import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { CertificateService } from '../certificate/services/certificate.service';
import { CertificateDTO } from '../model/certificate';
import { CsrDTO, KeyUsages } from '../model/csr';
import { CsrService } from './services/csr.service';

@Component({
  selector: 'app-csr',
  templateUrl: './csr.component.html',
  styleUrls: ['./csr.component.css'],
  providers: [MessageService],
})
export class CsrComponent implements OnInit {
  csrs: CsrDTO[] = [];
  certificates: CertificateDTO[] = [];
  displayCertificateTableDialog = false;
  extenstionsModal = false;
  basicConstraintIsCa = 'false';
  subjectName: string = '';
  selectedIssuerName: string = '';
  basicConstraint: string = 'Critical';
  aki: string = 'Critical';
  ski: string = 'Critical';

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

  criticalSubjectAlternativeName = 'Critical';
  criticalExtendedKeyUsage = 'Critical';
  criticalKeyUsage = 'Critical';
  constructor(
    private csrService: CsrService,
    private certificateService: CertificateService,
    private messageService: MessageService
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
    this.certificateService.getAllCACertificate().subscribe((res) => {
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
    var akicrit = true;
    var akiext = true;
    if (this.aki == 'None') {
      akicrit = false;
      akiext = false;
    }
    if (this.aki == 'Non-critical') {
      akicrit = false;
      akiext = true;
    }
    if (this.aki == 'Critical') {
      akicrit = true;
      akiext = true;
    }

    var skicrit = true;
    var skiext = true;
    if (this.ski == 'None') {
      skicrit = false;
      skiext = false;
    }
    if (this.ski == 'Non-critical') {
      skicrit = false;
      skiext = true;
    }
    if (this.ski == 'Critical') {
      skicrit = true;
      skiext = true;
    }

    var bccrit = true;
    if (this.basicConstraint == 'Critical') {
      bccrit = true;
    } else {
      bccrit = false;
    }

    var basicConstraints;
    var ca;
    if (this.basicConstraint == 'None') {
      basicConstraints = null;
    } else {
      if (this.basicConstraintIsCa == 'false') {
        ca = false;
      } else {
        ca = true;
      }
      basicConstraints = { ca: ca };
    }

    const extendedKeyUsage = this.extendedKeyUsagesSelected.map(
      (key) => key.code
    );
    var ekucrit = true;
    if (this.criticalExtendedKeyUsage == 'Critical') {
      ekucrit = true;
    } else {
      ekucrit = false;
    }

    const keyUsage = this.keyUsagesSelected.map((key) => key.code);
    var kuecrit = true;
    if (this.criticalKeyUsage == 'Critical') {
      kuecrit = true;
    } else {
      kuecrit = false;
    }

    var subjectAlternativeName;
    let names = new Map<number, string>();
    if (this.rfc822.trim() != '') {
      names.set(1, this.rfc822);
    }
    if (this.dnsName.trim() != '') {
      names.set(2, this.dnsName);
    }
    if (this.directoryName.trim() != '') {
      names.set(4, this.directoryName);
    }
    if (this.uri.trim() != '') {
      names.set(6, this.uri);
    }
    if (this.ipAddress.trim() != '') {
      names.set(7, this.ipAddress);
    }
    subjectAlternativeName = { names };
    var sancrit = true;
    if (this.criticalSubjectAlternativeName == 'Critical') {
      sancrit = true;
    } else {
      sancrit = false;
    }

    const extensions = {
      basicConstraints,
      extendedKeyUsage: { keyPurposes: extendedKeyUsage },
      keyUsage: { keyUsages: keyUsage },
      subjectAlternativeName,
      akiext,
      akicrit,
      skicrit,
      skiext,
      ekucrit,
      sancrit,
      kuecrit,
      bccrit,
    };

    this.csrService
      .signCsr(this.selectedIssuerName, this.subjectName, extensions)
      .subscribe((res) => {
        console.log(res);
        this.displayCertificateTableDialog = false;
        this.extenstionsModal = false;
        this.getAllCsrs();

        this.messageService.add({
          key: 'tc',
          severity: 'info',
          summary: 'Info',
          detail: res,
        });
      });
  }
}
