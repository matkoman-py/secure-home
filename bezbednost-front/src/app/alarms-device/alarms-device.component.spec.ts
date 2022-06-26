import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlarmsDeviceComponent } from './alarms-device.component';

describe('AlarmsDeviceComponent', () => {
  let component: AlarmsDeviceComponent;
  let fixture: ComponentFixture<AlarmsDeviceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AlarmsDeviceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AlarmsDeviceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
