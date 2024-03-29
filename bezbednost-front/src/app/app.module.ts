import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
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
import { InputTextareaModule } from 'primeng/inputtextarea';
import { DropdownModule } from 'primeng/dropdown';
import { LoginComponent } from './login/login.component';
import { PasswordModule } from 'primeng/password';
import { LogoutComponent } from './logout/logout.component';
import { TokenInterceptorInterceptor } from './token-interceptor.interceptor';
import { CreateUserComponent } from './create-user/create-user.component';
import { UserEstatesComponent } from './user-estates/user-estates.component';
import { LogsComponent } from './logs/logs.component';
import { CreateUserRequestComponent } from './create-user-request/create-user-request.component';
import { ApproveUserRequestComponent } from './approve-user-request/approve-user-request.component';
import { AlarmsSystemComponent } from './alarms-system/alarms-system.component';
import { AlarmsDeviceComponent } from './alarms-device/alarms-device.component';
import {CalendarModule} from 'primeng/calendar';
import { RuleComponent } from './rule/rule.component';
import {InputNumberModule} from 'primeng/inputnumber';

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
    CreateUserComponent,
    LoginComponent,
    LogoutComponent,
    UserEstatesComponent,
    LogsComponent,
    CreateUserRequestComponent,
    ApproveUserRequestComponent,
    AlarmsSystemComponent,
    AlarmsDeviceComponent,
    RuleComponent,
    
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
    InputTextareaModule,
    DropdownModule,
    PasswordModule,
    CalendarModule,
    InputNumberModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
