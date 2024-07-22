import { TestBed } from '@angular/core/testing';

import { PaymentmethodsService } from './paymentmethods.service';

describe('PaymentmethodsService', () => {
  let service: PaymentmethodsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PaymentmethodsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
