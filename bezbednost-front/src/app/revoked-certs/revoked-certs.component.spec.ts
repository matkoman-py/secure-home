import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevokedCertsComponent } from './revoked-certs.component';

describe('RevokedCertsComponent', () => {
  let component: RevokedCertsComponent;
  let fixture: ComponentFixture<RevokedCertsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RevokedCertsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RevokedCertsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
