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
  ];

  constructor() {}

  ngOnInit(): void {}
}
