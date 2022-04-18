import { TestBed } from '@angular/core/testing';

import { RevokedCertsService } from './revoked-certs.service';

describe('RevokedCertsService', () => {
  let service: RevokedCertsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RevokedCertsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
