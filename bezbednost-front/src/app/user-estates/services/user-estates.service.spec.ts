import { TestBed } from '@angular/core/testing';

import { UserEstatesService } from './user-estates.service';

describe('UserEstatesService', () => {
  let service: UserEstatesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserEstatesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
