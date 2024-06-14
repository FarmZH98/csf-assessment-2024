import { Component, Inject, inject, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { WebcamComponent, WebcamImage } from 'ngx-webcam';
import { Subject, Subscription } from 'rxjs';
import { UploadService } from '../upload.service';
// import { Upload } from '../model';
// import { FormRepository } from '../repo/form.repository';
// import { CameraService } from '../services/camera.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit, OnDestroy {

  // TODO: Task 1
  @ViewChild(WebcamComponent)
  webcam!: WebcamComponent;
  sub$!: Subscription
  trigger = new Subject<void>;
  height : number = 282;
  width : number = 500;

  private readonly uploadSvc = inject(UploadService)
  private readonly router = inject(Router)
  //num = parseFloat((<HTMLInputElement>document.getElementById("size")).value);

  ngOnInit(): void {
      this.webcam.trigger = this.trigger;
      this.sub$ = this.webcam.imageCapture.subscribe(
        this.snapshot.bind(this)
      )
      
      //console.log(this.num)
  }

  ngOnDestroy(): void {
      //this.sub$.unsubscribe();
  }

  // size1() {
  //   this.height = 282
  //   this.webcam.height=282
  // }

  // size2() {
  //   this.height = 375
  //   this.webcam.height=375
  // }

  // size3() {
  //   this.height = 333
  //   this.webcam.height=333
  // }

  // size4() {
  //   this.height = 500
  //   this.webcam.height=500
  // }

  snap(){
    this.trigger.next();
  }

  snapshot(webcamImg: WebcamImage) {
    //consider changing this to param and send to pic
    console.log("width: " + this.width + ", height:" + this.height)
    this.uploadSvc.imageData = webcamImg.imageAsDataUrl;
    
    this.router.navigate(['/pic'])//route to view 2
  }

}
