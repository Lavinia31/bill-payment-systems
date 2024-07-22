import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InvoicesPayComponent } from './invoices-pay.component';

describe('InvoicesPayComponent', () => {
  let component: InvoicesPayComponent;
  let fixture: ComponentFixture<InvoicesPayComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InvoicesPayComponent]
    });
    fixture = TestBed.createComponent(InvoicesPayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
