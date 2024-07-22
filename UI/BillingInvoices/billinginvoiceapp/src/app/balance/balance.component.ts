import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogBoxComponent } from '../dialog-box/dialog-box.component';
import { Client } from '../paymentmethods/clients';

@Component({
  selector: 'binv-balance',
  templateUrl: './balance.component.html',
  styleUrls: ['./balance.component.css']
})
export class BalanceComponent {


  constructor(public dialog: MatDialog, private http: HttpClient){}
    
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
