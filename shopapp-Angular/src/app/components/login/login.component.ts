import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { LoginDTO } from '../dtos/user/login.dto';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;
  phoneNumber: string;
  password: string;

  constructor(private router: Router, private userService : UserService ){
    this.phoneNumber = '';
    this.password = '';
  }

  onPhoneChange(){
    console.log(`Phone typed: ${this.phoneNumber}`)
  }

  login(){
    const message = `phone :${this.phoneNumber} `+
    `password :${this.password} `;

    const LoginDto:LoginDTO ={
      "phone_number": this.phoneNumber,
      "password": this.password
    }

    this.userService.login(LoginDto)
    .subscribe({
      next: (apiResponse: any) => {
        debugger
        console.log(apiResponse)
     console.log("login successfuly")

      },
      error: (error: HttpErrorResponse) => {
        debugger
        console.error('API Error:', error);
        alert('Đăng ký không thành công. Vui lòng thử lại sau.');
      },
      complete: () => {
        debugger
        console.log('API Request completed.');
      }
    });


  }



}
