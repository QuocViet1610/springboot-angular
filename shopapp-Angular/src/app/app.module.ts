import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';
import { OrderComponent } from './components/order/order.component';
import { OrderConfirmComponent } from './components/order-confirm/order-confirm.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { DetailProductComponent } from './components/detail-product/detail-product.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './Interceptors/token.interceptor';


  @NgModule({
    declarations: [
      AppComponent,
      HomeComponent,
      FooterComponent,
      HeaderComponent,
      OrderComponent,
      OrderConfirmComponent,
      LoginComponent,
      RegisterComponent,
      DetailProductComponent,

    ],
    imports: [
      BrowserModule,
      FormsModule,
      HttpClientModule
    ],
    providers: [
      {
        provide: HTTP_INTERCEPTORS,
        useClass: TokenInterceptor,
        multi: true,
      },

    ],
    bootstrap: [
      // AppComponent,
      // OrderComponent,
      // OrderConfirmComponent
      LoginComponent
      // RegisterComponent
      // DetailProductComponent

    ],

  })
export class AppModule { }
