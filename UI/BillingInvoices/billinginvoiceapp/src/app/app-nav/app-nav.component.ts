import { Component, inject } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { MatDialog } from '@angular/material/dialog';
import { DialogBoxComponent } from '../dialog-box/dialog-box.component';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Client } from '../paymentmethods/clients';

@Component({
  selector: 'binv-app-nav',
  templateUrl: './app-nav.component.html',
  styleUrls: ['./app-nav.component.css']
})
export class AppNavComponent {
  private breakpointObserver = inject(BreakpointObserver);

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );
   

      constructor(private formBuilder: FormBuilder, private http: HttpClient){}
    
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
