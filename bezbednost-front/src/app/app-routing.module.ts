import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CertificateInfoComponent } from './certificate-info/certificate-info.component';
import { CertificateComponent } from './certificate/certificate.component';
import { CsrComponent } from './csr/csr.component';
import { GenerateCsrComponent } from './generate-csr/generate-csr.component';

const routes: Routes = [
  { path: 'certificates', component: CertificateComponent },
  { path: 'generate-csr', component: GenerateCsrComponent },
  { path: 'certificate/:alias', component: CertificateInfoComponent },
  { path: 'csr', component: CsrComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
