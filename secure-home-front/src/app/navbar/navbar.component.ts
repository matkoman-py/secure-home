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
      label: 'All messages',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/all-messages',
      visible: this.role === 'ROLE_ADMIN',
    },
    {
      label: 'Devices',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/devices',
      visible: this.role === 'ROLE_USER',
    },
    {
      label: 'Device alarms',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/device-alarms',
      visible: this.role === 'ROLE_USER',
    },
    {
      label: 'Report',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/report',
      visible: this.role === 'ROLE_USER',
    },
    {
      label: 'Logout',
      icon: 'pi pi-fw pi-sign-out',
      routerLink: '/logout',
    },
    {
      label: 'Login',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/login',
    },
    {
      label: 'Messages',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/messages',
      visible: this.role === 'ROLE_USER',
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
      }, ];
    } else {
      this.items = [{
          label: 'All messages',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/all-messages',
          visible: this.role === 'ROLE_ADMIN',
        },
        {
          label: 'Devices',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/devices',
          visible: this.role === 'ROLE_USER',
        },
        {
          label: 'Device alarms',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/device-alarms',
          visible: this.role === 'ROLE_USER',
        },
        {
          label: 'Report',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/report',
          visible: this.role === 'ROLE_USER',
        },
        {
          label: 'Messages',
          icon: 'pi pi-fw pi-sign-in',
          routerLink: '/messages',
          visible: this.role === 'ROLE_USER',
        },
        {
          label: 'Logout',
          icon: 'pi pi-fw pi-sign-out',
          routerLink: '/logout',
        }
      ];
    }
  };

  setLogoutItems = () => {
    this.items = [{
      label: 'Login',
      icon: 'pi pi-fw pi-sign-in',
      routerLink: '/login',
    }, ];
  };

  ngOnInit(): void {
    this.setNavbarItems();
  }
}
