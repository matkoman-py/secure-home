import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Device } from 'src/app/model/Device';
import { Rule, RulePreview } from 'src/app/model/Rule';

@Injectable({
  providedIn: 'root'
})
export class RuleService {

  constructor(private http: HttpClient) { }

  getAllDevices(): Observable<Device[]> {

    return this.http.get<Device[]>('/api/rules/devices');
  }

  postRule(rule: Rule): Observable<String> {
    return this.http.post('/api/rules', rule, {
      responseType: 'text',
    });
  }

  getAllRules(): Observable<RulePreview[]> {

    return this.http.get<RulePreview[]>('/api/rules');
  }
}
