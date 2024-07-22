import { AfterViewInit, Component, EventEmitter, Input, OnInit, Output, ViewChild, inject,  } from '@angular/core';
import { Invoice } from '../invoices';
import { Observable } from 'rxjs';
import { InvoicesService } from '../services/invoices.service';
import { MatTableDataSource } from '@angular/material/table';
import { InvoiceDataSource } from '../invoices-datasource';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'binv-invoices-list',
  templateUrl: './invoices-list.component.html',
  styleUrls: ['./invoices-list.component.css'],
  
})
export class InvoicesListComponent  implements OnInit  {
 
  // @Input() invoices: Invoice[] = [];
  // @Output() selectedInvoice = new EventEmitter<Invoice>();
  
  // invoiceList:Invoice[] = [];

  displayedColumns = ['invoiceId',
  'invoiceType',
  'issuedDate',
  'dueDate',
  'amount',
  'services',
  'paymentStatus',
  'payInvoice'
  
  ];

  // selectInvoice(invoice: Invoice) {
  //   this.selectedInvoice.emit(invoice);
  // }

  invoiceList:Invoice[] =[];

  // constructor(private invoicesService: InvoicesService){}

  constructor( private http: HttpClient) {
  }

  

  ngOnInit(): void {
  
    this.http.get<Invoice[]>('http://localhost:8080/billing/v1/clients/9/getinvoices',
    {
    headers: new HttpHeaders({
    'Accept': 'application/json',
    'Content-Type': 'application/json'
    })
    }).subscribe(invoices =>{this.invoiceList = invoices} );
  }

    payAllInvoices() {
      const updatedUrl = `./billing/v1/clients/9/invoices/pay`;

      this.http.post(updatedUrl,
        {
          headers: new HttpHeaders({
          'Accept': 'application/json',
          'Content-Type': 'application/json'
          }),
          })
          .subscribe(() => {
          });
           window.location.reload();
    }

    payInvoice(invoiceId: number) {
      const updatedUrl = `http://localhost:8080/billing/v1/clients/9/invoices/${invoiceId}`;

      this.http.post(updatedUrl,
        {
          headers: new HttpHeaders({
          'Accept': 'application/json',
          'Content-Type': 'application/json'
          }),
          })
          .subscribe(() => {
          });
           window.location.reload();
    }
 

    // invoiceList: InvoiceDataSource;

    // constructor( private invoicesService: InvoicesService) {
    //   const invoiceList$ = this.invoicesService.getInvoices();
    //   this.invoiceList = new InvoiceDataSource(invoiceList$);
    // }



}
