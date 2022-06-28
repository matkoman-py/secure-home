import {
  Component,
  OnInit
} from '@angular/core';
import {
  MenuItem
} from 'primeng/api';
import {
  LoginService
} from '../login/services/login.service';
import {
  LogoutService
} from '../logout/services/logout.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  role: string | null = '';

  items: MenuItem[] = [{
      label: 'Certificates',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/certificates',
    },
    {
      label: 'Logs',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/logs',
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
    {
      label: 'Login',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/login',
    },
    {
      label: 'Device Alarms',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/alarms-device',
    },
    {
      label: 'System Alarms',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/alarms-system',
    },
  ];

  constructor(
    private loginService: LoginService,
    private logoutService: LogoutService
  ) {
    this.loginService.getUserRole.subscribe(() => {
      this.setNavbarItems();
    });
    this.logoutService.logout.subscribe(() => {
      this.setLogoutItems();
    });
  }

  setNavbarItems = () => {
    this.role = sessionStorage.getItem('role');

    if (this.role === '') {
      this.items = [{
          label: 'Login',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/login',
        },
        {
          label: 'Create User Request',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/create-user-request',
        },
      ];
    } else {
      this.items = [{
          label: 'Certificates',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/certificates',
          visible: this.role === 'ROLE_ADMIN',
        },
        {
          label: 'CSR',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/csr',
          visible: this.role === 'ROLE_ADMIN',
        },
        {
          label: 'Generate CSR',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/generate-csr',
          visible: this.role === 'ROLE_ADMIN' || this.role === 'ROLE_USER',
        },
        {
          label: 'Revoked Certificates',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/revoked-certificates',
          visible: this.role === 'ROLE_ADMIN',
        },
        {
          label: 'Logout',
          icon: 'pi pi-fw pi-sign-out',
          routerLink: '/logout',
        },
        {
          label: 'Users',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/create-user',
          visible: this.role === 'ROLE_ADMIN',
        },
        {
          label: 'Logs',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/logs',
          visible: this.role === 'ROLE_ADMIN'
        },
        {
          label: 'Approve User',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/approve-user-request',
          visible: this.role === 'ROLE_ADMIN'
        },
        {
          label: 'Device Alarms',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/alarms-device',
          visible: this.role === 'ROLE_ADMIN'
        },
        {
          label: 'System Alarms',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/alarms-system',
          visible: this.role === 'ROLE_ADMIN'
        },
      ];
    }
  };

  setLogoutItems = () => {
    this.items = [{
        label: 'Login',
        icon: 'pi pi-fw pi-sign-in',
        routerLink: '/login',
      },
      {
        label: 'Create User Request',
        icon: 'pi pi-fw pi-sign-in',
        routerLink: '/create-user-request',
      }
    ];
  };

  ngOnInit(): void {
    this.setNavbarItems();
  }
}
