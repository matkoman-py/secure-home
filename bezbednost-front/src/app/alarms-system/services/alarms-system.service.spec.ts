import { TestBed } from '@angular/core/testing';

import { AlarmsSystemService } from './alarms-system.service';

describe('AlarmsSystemService', () => {
  let service: AlarmsSystemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AlarmsSystemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
