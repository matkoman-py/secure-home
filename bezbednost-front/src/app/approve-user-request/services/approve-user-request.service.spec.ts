import { TestBed } from '@angular/core/testing';

import { ApproveUserRequestService } from './approve-user-request.service';

describe('ApproveUserRequestService', () => {
  let service: ApproveUserRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApproveUserRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
