import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateCsrComponent } from './generate-csr.component';

describe('GenerateCsrComponent', () => {
  let component: GenerateCsrComponent;
  let fixture: ComponentFixture<GenerateCsrComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GenerateCsrComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GenerateCsrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
