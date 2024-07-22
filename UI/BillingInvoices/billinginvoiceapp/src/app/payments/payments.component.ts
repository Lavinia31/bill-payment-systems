import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { Invoice } from '../invoices/invoices';
import { Payment } from './payments';

@Component({
  selector: 'binv-payments',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.css']
})
export class PaymentsComponent {
  displayedColumns = [
    'paymentId',
    'invoiceId',
    'paymentDate',
    'cardDigits',
    'statusCode',
    'statusMessage'
  ];



  paymentList:Payment[] =[];
  constructor( private http: HttpClient) {
  }


  ngOnInit(): void {
  
    this.http.get<Payment[]>('http://localhost:8080/billing/v1/clients/9/payments',
    {
    headers: new HttpHeaders({
    'Accept': 'application/json',
    'Content-Type': 'application/json'
    })
    }).subscribe(payments =>{this.paymentList = payments} );
  }

}
