import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserRequestDTO } from 'src/app/model/UserRequestDTO';

@Injectable({
  providedIn: 'root'
})
export class ApproveUserRequestService {

  constructor(private http: HttpClient) { }

  approve(id: number): Observable<Object> {
    return this.http.post<Object>('/api/usersapprove/' + id, {});
  }

  getAll(): Observable<UserRequestDTO[]> {
    return this.http.get<UserRequestDTO[]>('/api/user-requests/all', {});
  }
}
