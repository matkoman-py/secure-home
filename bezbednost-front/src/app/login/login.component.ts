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

  validLogin: boolean = true;

  constructor(private loginService: LoginService, private router: Router) {}

  onLogin = () => {
    const auth: Login = {
      username: this.username,
      password: this.password,
    };

    this.loginService.userLogin(auth).subscribe(
      (response: any) => {
        var role = this.findUserRole(response.body.accessToken);
        if (role !== undefined) {
          localStorage.setItem('role', role);
          localStorage.setItem('isLoggedIn', 'true');
          localStorage.setItem('token', response.body.accessToken);
          let user: any;
          user = jwt_decode(response.body.accessToken);
          localStorage.setItem('username', user.sub);
          this.loginService.emitLogin();

          if (role == 'ROLE_ADMIN') {
            this.router.navigate(['certificates']);
          } else if (role === 'zdravstveni_radnik') {
            this.router.navigate(['certificates']);
          }
          console.log(role + ' je uloga');
        }
      },
      () => {
        this.validLogin = false;
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
