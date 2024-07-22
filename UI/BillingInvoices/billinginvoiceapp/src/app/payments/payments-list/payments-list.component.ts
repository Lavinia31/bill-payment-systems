import { Component, Input } from '@angular/core';
import { Payment } from '../payments';

@Component({
  selector: 'binv-payments-list',
  templateUrl: './payments-list.component.html',
  styleUrls: ['./payments-list.component.css']
})
export class PaymentsListComponent {
  @Input() payments: Payment[] = [];
  displayedColumns = [
    'paymentId',
    'invoiceId',
    'paymentDate',
    'cardDigits',
    'statusCode',
    'statusMessage'
  ];
}
