import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserRequest } from "../../model/UserRequest";
import { User } from "../../model/User";
import { RoleUpdateInfo } from "src/app/model/RoleUpdateInfo";

@Injectable({
  providedIn: 'root',
})
export class CreateUserService {
  constructor(private http: HttpClient) {}

  createUser(userRequest: UserRequest): Observable<Object> {
    return this.http.post<Object>('/api/users/save', userRequest);
  }

  getAll(searchTerm:string): Observable<User[]> {
    let param = new HttpParams().set("search", searchTerm);
    return this.http.get<User[]>('/api/users/all', {params: param});
  }

  delete(id: number): Observable<String> {
    return this.http.delete('/api/users/delete/' + id, {responseType: 'text'});
  }

  update(roleUpdateInfo: RoleUpdateInfo): Observable<String> {
    return this.http.put<String>('/api/users/update', roleUpdateInfo);
  }
}