import { Component } from '@angular/core';
import {  Invoice } from './invoices';
import { InvoicesService } from './services/invoices.service';

@Component({
  selector: 'binv-invoices',
  templateUrl: './invoices.component.html',
  styleUrls: ['./invoices.component.css']
})
export class InvoicesComponent {

   invoice: Invoice[] = [];

  constructor(private invoicesService: InvoicesService){}

  ngOnInit(): void{
    this.invoicesService.getInvoices().subscribe(invoices => {
      this.invoice = invoices;
    });
  }

 
  // selectInvoice(invoice:Invoice) {
  //   console.log(invoice);
  // }

}
