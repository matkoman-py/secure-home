import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ReportDTO } from 'src/app/model/report';

@Injectable({
  providedIn: 'root',
})
export class ReportService {
  constructor(private http: HttpClient) {}

  getReport(before: string, after: string): Observable<ReportDTO> {
    return this.http.get<ReportDTO>('/api/devices/report', {
      params: {
        dateBefore: before,
        dateAfter: after,
      },
    });
  }
}
