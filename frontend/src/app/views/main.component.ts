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
      //this.webcam.captureImageData = true;
      this.sub$ = this.webcam.imageCapture.subscribe(
        this.snapshot.bind(this)
      )
      
      //console.log(this.num)
  }

  ngOnDestroy(): void {
      //this.sub$.unsubscribe();
  }


  snap(){
    this.trigger.next();
  }

  //unable to set appropriate height and width
  snapshot(webcamImg: WebcamImage) {
    //console.log("height>>>" + webcamImg.imageData.toString())
    //console.log("width>>>" + webcamImg.imageData.width)
    //console.log("width: " + this.width + ", height:" + this.height)
    this.uploadSvc.imageData = webcamImg.imageAsDataUrl;
    
    this.router.navigate(['/pic'])//route to view 2
  }

}
