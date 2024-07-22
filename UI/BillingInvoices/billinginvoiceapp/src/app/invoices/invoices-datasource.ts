import { MatTableDataSource } from "@angular/material/table";
import { Invoice } from "./invoices";
import { Observable, Subscription } from "rxjs";

export class InvoiceDataSource extends MatTableDataSource<Invoice> {
    private transactions: Invoice[] = [];
  
    private transactions$: Subscription;
  
    constructor(transactions: Observable<Invoice>) {
      super();
      this.transactions$ = transactions.subscribe(transactionList => {
        this.transactions.push(transactionList);
        this.data = this.transactions;
      });
    }
  
     override disconnect() {
      this.transactions$.unsubscribe();
      super.disconnect();
    }
  }