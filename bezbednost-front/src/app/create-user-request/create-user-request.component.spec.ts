import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateUserRequestComponent } from './create-user-request.component';

describe('CreateUserRequestComponent', () => {
  let component: CreateUserRequestComponent;
  let fixture: ComponentFixture<CreateUserRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateUserRequestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateUserRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
