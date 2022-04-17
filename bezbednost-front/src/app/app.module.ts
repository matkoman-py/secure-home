import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CertificateComponent } from './certificate/certificate.component';
import { NavbarComponent } from './navbar/navbar.component';
import { MenubarModule } from 'primeng/menubar';
import { TableModule } from 'primeng/table';
import { GenerateCsrComponent } from './generate-csr/generate-csr.component';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    CertificateComponent,
    NavbarComponent,
    GenerateCsrComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MenubarModule,
    TableModule,
    InputTextModule,
    FormsModule,
    CardModule,
    ButtonModule,
    ToastModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
