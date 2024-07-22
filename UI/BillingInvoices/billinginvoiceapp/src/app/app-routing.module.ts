import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InvoicesComponent } from './invoices/invoices.component';
import { PaymentsComponent } from './payments/payments.component';
import { PaymentmethodsComponent } from './paymentmethods/paymentmethods.component';
import { NotfoundComponent } from './notfound/notfound.component';
import { InvoicesPayComponent } from './invoices/invoices-pay/invoices-pay.component';
import { PaymentmethodChangeComponent } from './paymentmethods/paymentmethod-change/paymentmethod-change.component';

const routes: Routes = [
  {path:'invoices', component:InvoicesComponent},
  {path: 'invoices/:invoiceId', component:InvoicesPayComponent},
  {path: 'paymentmethods/changemethod', component:PaymentmethodChangeComponent},
  {path:'payments', component:PaymentsComponent},
  {path:'paymentmethods', component:PaymentmethodsComponent},
  {path: '',redirectTo: '/invoices', pathMatch: 'full'},
  {path: '**', component: NotfoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
