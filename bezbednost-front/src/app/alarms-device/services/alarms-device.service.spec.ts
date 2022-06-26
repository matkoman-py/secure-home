import { TestBed } from '@angular/core/testing';

import { AlarmsDeviceService } from './alarms-device.service';

describe('AlarmsDeviceService', () => {
  let service: AlarmsDeviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AlarmsDeviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
