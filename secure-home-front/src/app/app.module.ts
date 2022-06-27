import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { MenubarModule } from 'primeng/menubar';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { ToastModule } from 'primeng/toast';
import { RadioButtonModule } from 'primeng/radiobutton';
import { MultiSelectModule } from 'primeng/multiselect';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { DropdownModule } from 'primeng/dropdown';
import { PasswordModule } from 'primeng/password';
import { TokenInterceptorInterceptor } from './token-interceptor.interceptor';
import { CalendarModule } from 'primeng/calendar';

import { AppComponent } from './app.component';
import { DeviceAlarmsComponent } from './device-alarms/device-alarms.component';
import { DevicesComponent } from './devices/devices.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AllMessagesComponent } from './all-messages/all-messages.component';
import { ReportComponent } from './report/report.component';
import { UserMessagesComponent } from './user-messages/user-messages.component';

@NgModule({
  declarations: [
    AppComponent,
    DeviceAlarmsComponent,
    DevicesComponent,
    LoginComponent,
    LogoutComponent,
    NavbarComponent,
    AllMessagesComponent,
    ReportComponent,
    UserMessagesComponent,
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
