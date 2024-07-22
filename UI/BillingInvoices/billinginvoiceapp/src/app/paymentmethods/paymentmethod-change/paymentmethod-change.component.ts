import { Component, OnInit } from '@angular/core';
import { Client } from '../clients';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'binv-paymentmethod-change',
  templateUrl: './paymentmethod-change.component.html',
  styleUrls: ['./paymentmethod-change.component.css']
})
export class PaymentmethodChangeComponent implements OnInit {

  paymentmethodForm!: FormGroup;

  constructor(private formBuilder: FormBuilder){}

  ngOnInit(): void {
    this.paymentmethodForm = this.formBuilder.group({
      cardDigits: new FormControl(''),
      expirationDate: new FormControl(''),
      token: new FormControl(''),
    })
  }

}

