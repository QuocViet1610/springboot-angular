import { Injectable } from '@angular/core';
import { environment } from '../environment/environment.develop';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RoleService {
  private apiGetRoles = `${environment.apiBaseUrl}/roles`;

  private createHeaders():HttpHeaders{
    return new HttpHeaders({  'Content-Type': 'application/json',
      'Accept-Language':'en'})
  }

  constructor(private http: HttpClient) {
  }

  getRole():Observable<any>{
    return this.http.get<any[]>(this.apiGetRoles);
  }

}
