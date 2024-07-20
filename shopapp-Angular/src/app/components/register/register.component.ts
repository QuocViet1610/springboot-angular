import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { RegisterDTO } from '../dtos/user/resgiter.dto';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

export class RegisterComponent {
  @ViewChild('registerForm') registerForm!: NgForm;
  phoneNumber: string;
  password: string;
  retypePassword: string;
  fullName: string;
  address:string;
  isAccepted: boolean;
  dateOfBirth: Date;
  showPassword: boolean = false;
  chekcMatchRetypePassword : boolean = false;


  constructor( private userService: UserService, private router: Router ){
    this.phoneNumber = '';
    this.password = '';
    this.retypePassword = '';
    this.fullName = '';
    this.address = '';
    this.isAccepted = true;
    this.dateOfBirth = new Date();
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18);
  }

  onPhoneChange(){
    console.log(`Phone typed: ${this.phoneNumber}`)
  }

  register(){
    const message = `phone :${this.phoneNumber} `+
    `password :${this.password} `+
    `retypePassword :${this.retypePassword} `+
    `fullName :${this.fullName} `+
    `address :${this.address} `;
    alert(`Phone typed: ${message}`)
    if (this.password !== this.retypePassword) {
      this.chekcMatchRetypePassword  = true;
    }

    const apiUrl = "http://localhost:8080/api/v1/User/register";
    const headers = new HttpHeaders({'Content-Type' : 'application/json'})

    const registerDTO:RegisterDTO = {
      "fullname": this.fullName,
      "phone_number": this.phoneNumber,
      "address": this.address,
      "password": this.password,
      "retype_password": this.retypePassword,
      "date_of_birth": this.dateOfBirth,
      "facebook_account_id": 0,
      "google_account_id": 0,
      "role_id": 1
  }

    this.userService.register(registerDTO)
    .subscribe({
      next: (apiResponse: any) => {
        console.log('API Response:', apiResponse);
        const confirmation = window.confirm('Đăng ký thành công, mời bạn đăng nhập. Bấm "OK" để chuyển đến trang đăng nhập.');
        if (confirmation) {
          this.router.navigate(['/login']);
        }
      },
      error: (error: HttpErrorResponse) => {
        console.error('API Error:', error);
        alert('Đăng ký không thành công. Vui lòng thử lại sau.');
      },
      complete: () => {
        console.log('API Request completed.');
      }
    });



  }

  checkAge() {
    if (this.dateOfBirth) {
      const today = new Date();
      const birthDate = new Date(this.dateOfBirth);
      let age = today.getFullYear() - birthDate.getFullYear();
      const monthDiff = today.getMonth() - birthDate.getMonth();
      if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
      }

      if (age < 18) {
        this.registerForm.form.controls['dateOfBirth'].setErrors({ 'invalidAge': true });
      } else {
        this.registerForm.form.controls['dateOfBirth'].setErrors(null);
      }
    }
  }
}
