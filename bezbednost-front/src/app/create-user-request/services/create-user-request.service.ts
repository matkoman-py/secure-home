import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserRequestDTO } from 'src/app/model/UserRequestDTO';

@Injectable({
  providedIn: 'root'
})
export class CreateUserRequestService {

  constructor(private http: HttpClient) { }

  createUserRequest(userRequestDTO: UserRequestDTO): Observable<Object> {
    return this.http.post<Object>('/api/user-requests/save', userRequestDTO);
  }
}
