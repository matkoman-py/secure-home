import { HttpClient } from '@angular/common/http';
import { Injectable, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LogoutService {
  @Output() logout: EventEmitter<any> = new EventEmitter();

  constructor(private http: HttpClient) {}

  // logoutFunction = () => {
  //   this.http.put<any>('/api/auth/logout', {});
  //   this.logout.emit();
  // };


  userLogout(): Observable<any> {
    return this.http.put<any>('/api/auth/logout', {});
  }
  emitLogout = () => {
    this.logout.emit();
  }
}
