import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../service/user.service';
import { LoginDTO } from '../dtos/user/login.dto';
import { HttpErrorResponse } from '@angular/common/http';
import { TokenService } from 'src/app/service/token.service';
import { LoginResponse } from 'src/app/responese/login.response';
import { Role } from 'src/app/modules/role';
import { RoleService } from 'src/app/service/role.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;
  phoneNumber: string;
  password: string;
  roles: Role[] = []; // Mảng roles
  selectedRole: Role | undefined;

  constructor(
    private router: Router,
    private userService : UserService,
    private tokenService : TokenService,
    private roleService : RoleService

    ){
    this.phoneNumber = '';
    this.password = '';
  }

  onPhoneChange(){
    console.log(`Phone typed: ${this.phoneNumber}`)
  }


  onPhoneChangerole (){
    console.log(`PselectedRole: ${ this.selectedRole?.name}`)
  }

  ngOnInit(){
    this.roleService.getRole()
    .subscribe({
      next: (role: Role[]) => {
        debugger
        this.roles = role;
        this.selectedRole = this.roles.length > 0 ? this.roles[0] : undefined;
     console.log("login successfuly")

      },
      error: (error: HttpErrorResponse) => {
        debugger
        console.error('API Error:', error);
      },
      complete: () => {
        debugger
        console.log('API Request completed.');
      }
    });


  }





  login(){
    const message = `phone :${this.phoneNumber} `+
    `password :${this.password} `;

    const LoginDto:LoginDTO ={
      phone_number: this.phoneNumber,
      password: this.password,
      role_id: this.selectedRole?.id ?? 1
    };

    this.userService.login(LoginDto)
    .subscribe({
      next: (apiResponse: LoginResponse) => {
        debugger
        const { token }  = apiResponse
        this.tokenService.setToken(token);
        console.log(apiResponse)
        console.log("login successfuly")

      },
      error: (error: HttpErrorResponse) => {
        debugger
        console.error('API Error:', error);
        alert(error.error.message);
      },
      complete: () => {
        debugger
        console.log('API Request completed.');
      }
    });


  }



}
