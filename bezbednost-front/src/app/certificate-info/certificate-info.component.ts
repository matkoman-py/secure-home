import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CertificateInfoService } from './services/certificate-info.service';
import { CertificateDTO, ExtensionsDTO } from '../model/certificate';

@Component({
  selector: 'app-certificate-info',
  templateUrl: './certificate-info.component.html',
  styleUrls: ['./certificate-info.component.css']
})
export class CertificateInfoComponent implements OnInit {

  constructor(private route: ActivatedRoute, private certificateInfoService: CertificateInfoService) { }

  certificate: CertificateDTO = {algorithm:"",entryName:"",expirationDate:Date(),issuerName:"",keySize:0,serialNo:0,startDate:Date(),subjectName:"",version:0, format:"", valid:false, reason: ""};
  extensions: ExtensionsDTO = {aki:"",akicrit:false,bccrit:false,ca:-1,ekucrit:false,extendedKeyUsages:[],keyUsages:[],kucrit:false,sancrit:false,ski:"",skicrit:false,subjectAlternativeNames:[]};
  autoResize: boolean = true;
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      var self = this;
      var fileName = "";
      if(params['ks'] == "revoke") {
        fileName = "keystore-deleted.jks";
      } else {
        fileName = "keystore.jks";
      }
      this.certificateInfoService.getCertificate(params['alias'], fileName).subscribe(res => {
        var that = self;
        self.certificate = res;
        this.certificateInfoService.getExtension(params['alias'], fileName).subscribe(resp => {
          this.extensions = resp
        })
      })
   });
  }

}
