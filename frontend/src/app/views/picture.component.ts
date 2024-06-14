import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UploadService } from '../upload.service';
import { Router } from '@angular/router';
import { dataToImage } from '../utils';

@Component({
  selector: 'app-picture',
  templateUrl: './picture.component.html',
  styleUrl: './picture.component.css'
})
export class PictureComponent implements OnInit {

  imageData = ""
  form!: FormGroup;
  blob!: Blob;
  isSaved = false
  private readonly uploadSvc = inject(UploadService)
  private readonly router = inject(Router)
  private readonly fb = inject(FormBuilder)

    // TODO: Task 2
  ngOnInit(): void {
    if(!this.uploadSvc.imageData){
      this.router.navigate(['/'])
    }

    //image size needs to fix?
    this.imageData = this.uploadSvc.imageData
    console.log(this.uploadSvc.imageData)

    this.form = this.fb.group(
      {
        title: this.fb.control<string>('', [Validators.required, Validators.minLength(5)]),
        comments: this.fb.control<string>(''),
      }
    );
  }

  // TODO: Task 3
  post(): void {
    
    this.blob = dataToImage(this.imageData);
    const formVal = this.form.value
    this.uploadSvc.upload(formVal, this.blob).then((result) => {
      console.log(result)
      this.router.navigate(['/'])
      //return this.isSaved
      this.isSaved = true
    }).catch( e => {
      console.error(e)
      alert(e.error.message)
    })
  }

  isPictureSaved() {
    return this.isSaved
  }

}
