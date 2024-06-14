import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MainComponent } from './views/main.component';
import { PictureComponent } from './views/picture.component';
import { WebcamModule } from 'ngx-webcam';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { leaveMainPage } from './guard';

const appRoutes: Routes = [
  {path: '', component: MainComponent},
  {path: 'pic', component: PictureComponent,
    canDeactivate: [leaveMainPage]
  },
  { path: '**', redirectTo: '/',  pathMatch:"full" }
]

@NgModule({
  declarations: [
    AppComponent, MainComponent, PictureComponent
  ],
  imports: [
    BrowserModule,
    WebcamModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, { useHash: true })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
