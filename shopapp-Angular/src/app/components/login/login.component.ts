import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;
  phoneNumber: string;
  password: string;

  constructor( private router: Router ){
    this.phoneNumber = '';
    this.password = '';
  }

  onPhoneChange(){
    console.log(`Phone typed: ${this.phoneNumber}`)
  }

  register(){
    const message = `phone :${this.phoneNumber} `+
    `password :${this.password} `;



  }



}
