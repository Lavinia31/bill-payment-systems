import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DialogBoxComponent } from './dialog-box/dialog-box.component';
import { MatDialog } from '@angular/material/dialog';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Client } from './paymentmethods/clients';

@Component({
  selector: 'binv-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'billinginvoiceapp';


  constructor(public dialog: MatDialog,private formBuilder: FormBuilder, private http: HttpClient){}
    
  openDialog(){
      this.dialog.open(DialogBoxComponent,{
        width:'250px',
      data:'right click'
      })
      }
    
 
  clientList!: Client;

  ngOnInit(): void {

    this.http.get<Client>('http://localhost:8080/billing/v1/clients/9',
    {
    headers: new HttpHeaders({
    'Accept': 'application/json',
    'Content-Type': 'application/json'
    })
    }).subscribe(client =>{this.clientList = client;
  });

  }








}

