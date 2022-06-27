import { HttpClient } from '@angular/common/http';
import { Token } from '@angular/compiler';
import { Injectable, Output, EventEmitter } from '@angular/core';
import { Observable } from 'rxjs';
import { Login } from 'src/app/model/login';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  @Output() getUserRole: EventEmitter<any> = new EventEmitter();

  constructor(private http: HttpClient) {}

  userLogin(auth: Login): Observable<any> {
    return this.http.post<Token>('/api/auth/login', auth, {
      responseType: 'json',
      observe: 'response',
    });
  }

  emitLogin = () => {
    this.getUserRole.emit();
  };
}
