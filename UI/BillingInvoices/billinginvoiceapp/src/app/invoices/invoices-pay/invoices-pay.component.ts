import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { map } from 'rxjs';


@Component({
  selector: 'binv-invoices-pay',
  templateUrl: './invoices-pay.component.html',
  styleUrls: ['./invoices-pay.component.css']
})
export class InvoicesPayComponent {
  constructor(private router: ActivatedRoute) {}

invoiceId$ = this.router.paramMap.pipe(map((params) => params.get('invoiceId')));

  // invoiceId: number = 0;
  
  ngOnInit(): void{
    // this.router.params.subscribe((params) => {this.invoiceId = params['invoiceId']});
    
  }

}
