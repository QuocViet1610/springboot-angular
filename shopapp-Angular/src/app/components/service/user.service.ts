  import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../dtos/user/resgiter.dto';
import { environment } from '../environment/environment.develop';
import { LoginDTO } from '../dtos/user/login.dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiRegister = `${environment.apiBaseUrl}/users/register`;
  private apiUrl = `${environment.apiBaseUrl}/User/login`;
  private apiConfig = {
    headers: this.createHeaders(),
  }


  private createHeaders():HttpHeaders{
    return new HttpHeaders({ 'Content-Type':'application/json'})
  }

  constructor(private http: HttpClient) {
  }
  register(RegisterDTO:RegisterDTO):Observable<any>{
    return this.http.post(this.apiRegister, RegisterDTO,this.apiConfig)
  }

  login(LoginDto:LoginDTO):Observable<any>{
    return this.http.post(this.apiRegister, LoginDto,this.apiConfig)
  }
}