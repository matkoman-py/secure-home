import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LogoutService } from './services/logout.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css'],
})
export class LogoutComponent implements OnInit {
  constructor(private router: Router, private logoutService: LogoutService) {}

  ngOnInit(): void {
    sessionStorage.setItem('role', '');
    sessionStorage.setItem('token', '');
    sessionStorage.setItem('isLoggedIn', 'false');
    sessionStorage.setItem('username', '');
    this.router.navigate(['login']);
    this.logoutService.logoutFunction();
  }
}
