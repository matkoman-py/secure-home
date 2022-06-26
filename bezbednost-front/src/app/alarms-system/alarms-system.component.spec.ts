import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlarmsSystemComponent } from './alarms-system.component';

describe('AlarmsSystemComponent', () => {
  let component: AlarmsSystemComponent;
  let fixture: ComponentFixture<AlarmsSystemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AlarmsSystemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AlarmsSystemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
