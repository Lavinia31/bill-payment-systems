import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Additionalbalance } from './additionalbalance';
import { Location } from '@angular/common';

@Component({
  selector: 'binv-dialog-box',
  templateUrl: './dialog-box.component.html',
  styleUrls: ['./dialog-box.component.css']
})
export class DialogBoxComponent {

  constructor(private location: Location, private formBuilder: FormBuilder, private http: HttpClient,private router: Router, 
    public dialogRef: MatDialogRef<DialogBoxComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

 balanceForm!: FormGroup;
 
 ngOnInit(): void {

   this.balanceForm = this.formBuilder.group({
     additionalbalance: [''],
   });
 }
  
   updateBalance(){
     const additionalBalance:Additionalbalance = {
      balance: this.balanceForm.get('additionalbalance')?.value,
     };

     const updatedUrl = `http://localhost:8080/billing/v1/clients/9?additionalBalance=${additionalBalance.balance}`;

     this.http.put(updatedUrl,additionalBalance,
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
}







