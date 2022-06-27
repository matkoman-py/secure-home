import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { LoginService } from './services/login.service';
import jwt_decode from 'jwt-decode';
import { Login } from '../model/login';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [MessageService],
})
export class LoginComponent implements OnInit {
  username: string = '';
  password: string = '';
  PASSWORD_PATTERN: string = '^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$';

  validLogin: boolean = true;

  constructor(
    private loginService: LoginService,
    private router: Router,
    private messageService: MessageService
  ) {}

  onLogin = () => {
    const auth: Login = {
      username: this.username,
      password: this.password,
    };

    if (!/^[A-Za-z0-9_.]+$/.test(auth.username)) {
      // Usernames can only use letters, numbers, underscores, and periods.
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Warning',
        detail:
          'Username is in bad format (Must contain only alphanumerical characters and special characters like ., - and _)',
      });
      return;
    }

    if (!auth.password.match(this.PASSWORD_PATTERN)) {
      //First name can only use letters and hypen
      this.messageService.add({
        key: 'tc',
        severity: 'error',
        summary: 'Warning',
        detail:
          'Password is in bad format (Must contain at least 8 characters, with one small letter, one capital letter and at least 3 numbers)',
      });
      return;
    }

    this.loginService.userLogin(auth).subscribe(
      (response: any) => {
        var role = this.findUserRole(response.body.accessToken);
        if (role !== undefined) {
          sessionStorage.setItem('role', role);
          sessionStorage.setItem('isLoggedIn', 'true');
          sessionStorage.setItem('token', response.body.accessToken);
          let user: any;
          user = jwt_decode(response.body.accessToken);
          sessionStorage.setItem('username', user.sub);
          this.loginService.emitLogin();

          if (role == 'ROLE_ADMIN') {
            this.router.navigate(['all-messages']);
          } else if (role === 'ROLE_USER') {
            this.router.navigate(['devices']);
          }
          console.log(role + ' je uloga');
        }
      },
      (err) => {
        this.messageService.add({
          key: 'tc',
          severity: 'error',
          summary: 'Warning',
          detail: err.error,
        });
      }
    );
  };

  findUserRole(token: any) {
    let user: any;

    if (token) {
      user = jwt_decode(token);
    }
    console.log(user);
    if (user !== undefined) {
      if (user.roles.includes('ROLE_ADMIN')) {
        return 'ROLE_ADMIN';
      } else {
        return 'ROLE_USER';
      }
    }
    return undefined;
  }

  checkEmptyFields() {
    return (
      this.username === '' ||
      this.username === undefined ||
      this.password === '' ||
      this.password === undefined
    );
  }

  ngOnInit(): void {}
}
