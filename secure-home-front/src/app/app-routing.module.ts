import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllMessagesComponent } from './all-messages/all-messages.component';
import { AuthGuard } from './app-routing/auth.guard';
import { DeviceAlarmsComponent } from './device-alarms/device-alarms.component';
import { DevicesComponent } from './devices/devices.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { ReportComponent } from './report/report.component';
import { UserMessagesComponent } from './user-messages/user-messages.component';

const routes: Routes = [
  {
    path: 'devices',
    component: DevicesComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_USER'],
    },
  },
  {
    path: 'device-alarms',
    component: DeviceAlarmsComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_USER'],
    },
  },
  {
    path: 'report',
    component: ReportComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_USER'],
    },
  },
  {
    path: 'all-messages',
    component: AllMessagesComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_ADMIN'],
    },
  },
  {
    path: 'messages',
    component: UserMessagesComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_USER'],
    },
  },
  { path: 'login', component: LoginComponent },
  {
    path: 'logout',
    component: LogoutComponent,
  },
  {
    path: '**',
    canActivate: [AuthGuard],
    component: LoginComponent,
    data: {
      expectedRoles: [],
    },
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
