import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApproveUserRequestComponent } from './approve-user-request.component';

describe('ApproveUserRequestComponent', () => {
  let component: ApproveUserRequestComponent;
  let fixture: ComponentFixture<ApproveUserRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApproveUserRequestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApproveUserRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
