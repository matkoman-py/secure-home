import { HttpClient } from '@angular/common/http';
import { Injectable, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LogoutService {
  @Output() logout: EventEmitter<any> = new EventEmitter();

  constructor(private http: HttpClient) {}

  logoutFunction = () => {
    this.http.put<any>('/api/auth/logout', {});
    this.logout.emit();
  };
}
