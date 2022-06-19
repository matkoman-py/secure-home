import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Estate } from 'src/app/model/estate';

@Injectable({
  providedIn: 'root'
})
export class UserEstatesService {

  constructor(private http: HttpClient) { }

  getEstatesForUser(id:number): Observable<Estate[]> {
    return this.http.get<Estate[]>('/api/users/user/estates/'+ id);
  }

  getAllEstates(): Observable<Estate[]> {
    return this.http.get<Estate[]>('/api/users/user/estates');
  }

  addEstateToUser(userId: number, estateId: number): Observable<Estate> {
    return this.http.post<Estate>('/api/users/user/'+userId+'/'+estateId, null);
  }
}
