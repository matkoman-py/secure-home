import { TestBed } from '@angular/core/testing';

import { AllMessagesService } from './all-messages.service';

describe('AllMessagesService', () => {
  let service: AllMessagesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AllMessagesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
