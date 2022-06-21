import { TestBed } from '@angular/core/testing';

import { CreateUserRequestService } from './create-user-request.service';

describe('CreateUserRequestService', () => {
  let service: CreateUserRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateUserRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
