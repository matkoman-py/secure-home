import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CertificateComponent } from './certificate/certificate.component';
import { NavbarComponent } from './navbar/navbar.component';
import { MenubarModule } from 'primeng/menubar';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { CertificateInfoComponent } from './certificate-info/certificate-info.component';
import { CsrComponent } from './csr/csr.component';
import { DialogModule } from 'primeng/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { GenerateCsrComponent } from './generate-csr/generate-csr.component';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { ToastModule } from 'primeng/toast';
import { RadioButtonModule } from 'primeng/radiobutton';
import { MultiSelectModule } from 'primeng/multiselect';
import { RevokedCertsComponent } from './revoked-certs/revoked-certs.component';
@NgModule({
  declarations: [
    AppComponent,
    CertificateComponent,
    NavbarComponent,
    CertificateInfoComponent,
    CsrComponent,
    GenerateCsrComponent,
    CertificateInfoComponent,
    RevokedCertsComponent,
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MenubarModule,
    TableModule,
    ButtonModule,
    DialogModule,
    InputTextModule,
    FormsModule,
    CardModule,
    ToastModule,
    RadioButtonModule,
    MultiSelectModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
