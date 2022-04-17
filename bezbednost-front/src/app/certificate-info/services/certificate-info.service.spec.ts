import { TestBed } from '@angular/core/testing';

import { CertificateInfoService } from './certificate-info.service';

describe('CertificateInfoService', () => {
  let service: CertificateInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CertificateInfoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
