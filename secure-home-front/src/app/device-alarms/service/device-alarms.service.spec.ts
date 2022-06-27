import { TestBed } from '@angular/core/testing';

import { DeviceAlarmsService } from './device-alarms.service';

describe('DeviceAlarmsService', () => {
  let service: DeviceAlarmsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeviceAlarmsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
