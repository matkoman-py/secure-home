import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CertificateInfoComponent } from './certificate-info/certificate-info.component';
import { CertificateComponent } from './certificate/certificate.component';

const routes: Routes = [
  { path: 'certificates', component: CertificateComponent },
  {path: 'certificate/:alias', component:CertificateInfoComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
