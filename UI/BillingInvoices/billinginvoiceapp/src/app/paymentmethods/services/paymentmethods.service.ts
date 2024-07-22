import { Inject, Injectable } from '@angular/core';
import { Client } from '../clients';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppConfig } from 'src/app/AppConfig/appconfig.interface';
import { APP_SERVICE_CONFIG } from 'src/app/AppConfig/appconfig.service';

@Injectable({
  providedIn: 'root'
})
export class PaymentmethodsService {
  client : Client[] = [ 
    ];
  
    constructor(@Inject(APP_SERVICE_CONFIG) private config: AppConfig,
     private http: HttpClient ) {
      console.log('Method Service is initialized...');
      console.log(this.config.apiEndpoint);
    }
  
    getClients():Observable<any>{
      return this.http.get<Client[]>('http://localhost:8080/billing/v1/clients/3',
      {
      headers: new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
      })
      });
    }

}
