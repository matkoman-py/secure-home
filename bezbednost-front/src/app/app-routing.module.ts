import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CertificateInfoComponent } from './certificate-info/certificate-info.component';
import { CertificateComponent } from './certificate/certificate.component';
import { CreateUserComponent } from './create-user/create-user.component';
import { CsrComponent } from './csr/csr.component';
import { GenerateCsrComponent } from './generate-csr/generate-csr.component';
import { RevokedCertsComponent } from './revoked-certs/revoked-certs.component';

const routes: Routes = [
  { path: 'certificates', component: CertificateComponent },
  { path: 'generate-csr', component: GenerateCsrComponent },
  { path: 'certificate/:ks/:alias', component: CertificateInfoComponent },
  { path: 'csr', component: CsrComponent },
  { path: 'revoked-certificates', component: RevokedCertsComponent },
  { path: 'create-user', component: CreateUserComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
