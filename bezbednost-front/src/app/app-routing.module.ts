import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './app-routing/auth.guard';
import { CertificateInfoComponent } from './certificate-info/certificate-info.component';
import { CertificateComponent } from './certificate/certificate.component';
import { CreateUserComponent } from './create-user/create-user.component';
import { CsrComponent } from './csr/csr.component';
import { GenerateCsrComponent } from './generate-csr/generate-csr.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { LogsComponent } from './logs/logs.component';
import { RevokedCertsComponent } from './revoked-certs/revoked-certs.component';
import { UserEstatesComponent } from './user-estates/user-estates.component';

const routes: Routes = [
  {
    path: 'create-user',
    component: CreateUserComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_ADMIN'],
    },
  },
  {
    path: 'certificates',
    component: CertificateComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_ADMIN'],
    },
  },
  {
    path: 'logs',
    component: LogsComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_ADMIN'],
    },
  },
  {
    path: 'generate-csr',
    component: GenerateCsrComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_ADMIN', 'ROLE_USER'],
    },
  },
  {
    path: 'user-estates/:id',
    component: UserEstatesComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_ADMIN'],
    },
  },
  {
    path: 'certificate/:ks/:alias',
    component: CertificateInfoComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_ADMIN'],
    },
  },
  {
    path: 'csr',
    component: CsrComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_ADMIN'],
    },
  },
  {
    path: 'revoked-certificates',
    component: RevokedCertsComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_ADMIN'],
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
