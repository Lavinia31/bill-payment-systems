import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentmethodChangeComponent } from './paymentmethod-change.component';

describe('PaymentmethodChangeComponent', () => {
  let component: PaymentmethodChangeComponent;
  let fixture: ComponentFixture<PaymentmethodChangeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaymentmethodChangeComponent]
    });
    fixture = TestBed.createComponent(PaymentmethodChangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
