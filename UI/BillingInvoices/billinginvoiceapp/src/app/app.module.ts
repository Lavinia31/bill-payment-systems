import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InvoicesComponent } from './invoices/invoices.component';
import { InvoicesListComponent } from './invoices/invoices-list/invoices-list.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PaymentsComponent } from './payments/payments.component';
import { PaymentmethodsComponent } from './paymentmethods/paymentmethods.component';
import { AppNavComponent } from './app-nav/app-nav.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { NotfoundComponent } from './notfound/notfound.component';
import { InvoicesPayComponent } from './invoices/invoices-pay/invoices-pay.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { APP_CONFIG, APP_SERVICE_CONFIG } from './AppConfig/appconfig.service';
import {MatDialogModule} from '@angular/material/dialog';
import { DialogBoxComponent } from './dialog-box/dialog-box.component';
import { PaymentsListComponent } from './payments/payments-list/payments-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {CommonModule, JsonPipe} from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { BalanceComponent } from './balance/balance.component';

@NgModule({
  declarations: [
    AppComponent,
    InvoicesComponent,
    InvoicesListComponent,
    PaymentsComponent,
    PaymentmethodsComponent,
    AppNavComponent,
    NotfoundComponent,
    InvoicesPayComponent,
    DialogBoxComponent,
    PaymentsListComponent,
    BalanceComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatDialogModule,
    FormsModule,
    MatInputModule,
    CommonModule,
    JsonPipe,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatPaginatorModule,
  ],
  providers: [
    {
      provide: APP_SERVICE_CONFIG,
      useValue: APP_CONFIG,
      
    },
    { provide: JsonPipe },  
    // {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
