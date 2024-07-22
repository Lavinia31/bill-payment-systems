import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Client } from './clients';
import { PaymentmethodsService } from './services/paymentmethods.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Invoice } from '../invoices/invoices';
import { NgIf } from '@angular/common';
import { PaymentMethod } from './paymentmethod';
import { Router } from '@angular/router';

@Component({
  selector: 'binv-paymentmethods',
  templateUrl: './paymentmethods.component.html', 
   styleUrls: ['./paymentmethods.component.css']
})
export class PaymentmethodsComponent implements OnInit {
  showSaveButton: boolean = false;
  // disabledButton: boolean = true;


  // clientList: Client;
  paymentmethodForm!: FormGroup;
  clientList!: Client;
  disabledForm: boolean = true;
  // disabledButton: boolean = true;

  constructor( private formBuilder: FormBuilder, private http: HttpClient,private router: Router){}

  ngOnInit(): void {

    this.http.get<Client>('http://localhost:8080/billing/v1/clients/9',
    {
    headers: new HttpHeaders({
    'Accept': 'application/json',
    'Content-Type': 'application/json'
    })
    }).subscribe(client =>{this.clientList = client;

    this.paymentmethodForm.patchValue({
      cardDigits: this.clientList.paymentMethod.cardDigits,
      expirationDate: this.clientList.paymentMethod.expirationDate,
      token: this.clientList.paymentMethod.token,
    });
  });

    this.paymentmethodForm = this.formBuilder.group({
      cardDigits: new FormControl({value: '', disabled: this.disabledForm}),
      expirationDate: new FormControl({value: '', disabled: this.disabledForm}),
      token: new FormControl({value: '', disabled: this.disabledForm}),
    });
  
    
  }

  updateMethod(){
    if (this.disabledForm) {
      console.log('1');

      this.paymentmethodForm.controls['cardDigits'].enable();
      this.paymentmethodForm.controls['cardDigits'].setValue('');
      this.paymentmethodForm.controls['expirationDate'].enable();
      this.paymentmethodForm.controls['expirationDate'].setValue('');
      this.paymentmethodForm.controls['token'].enable();
      this.paymentmethodForm.controls['token'].setValue('');
      // this.disabledButton = false;
      this.showSaveButton = !this.showSaveButton;
    // this.disabledButton = !this.disabledButton;
    }}
    
    saveMethod(){
      console.log('2');

      const paymentmethod:PaymentMethod = {

        cardDigits : this.paymentmethodForm.get('cardDigits')?.value,
        expirationDate :  this.paymentmethodForm.get('expirationDate')?.value,
        token:  this.paymentmethodForm.get('token')?.value,
    
      };

      this.http.put('http://localhost:8080/billing/v1/clients/9/paymentMethod',paymentmethod,
      {
        headers: new HttpHeaders({
        'Accept': 'application/json',
        'Content-Type': 'application/json'
        }),
        })
        .subscribe(() => {
        this.router.navigateByUrl('/paymentmethods', { skipLocationChange: true }).then(() => { this.router.navigate(['/paymentmethods']); });
      });
      
      this.paymentmethodForm.disable();
      // this.disabledButton = true;
      this.showSaveButton = !this.showSaveButton;
    
  }
   
  
  
}









