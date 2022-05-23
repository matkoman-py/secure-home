import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  items: MenuItem[] = [
    {
      label: 'Certificates',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/certificates',
    },
    {
      label: 'CSR',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/csr',
    },
    {
      label: 'Generate CSR',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/generate-csr',
    },
    {
      label: 'Revoked Certificates',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/revoked-certificates',
    },
    {
      label: 'Users',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/create-user',
    },
  ];

  constructor() {}

  ngOnInit(): void {}
}
