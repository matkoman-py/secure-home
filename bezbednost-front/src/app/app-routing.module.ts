import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CertificateComponent } from './certificate/certificate.component';
import { GenerateCsrComponent } from './generate-csr/generate-csr.component';

const routes: Routes = [
  { path: 'certificates', component: CertificateComponent },
  { path: 'generate-csr', component: GenerateCsrComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
