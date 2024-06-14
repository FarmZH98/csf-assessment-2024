import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { firstValueFrom } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UploadService {


  imageData = ""
  private readonly httpClient = inject(HttpClient)

  // TODO: Task 3.
  // You may add add parameters to the method
  upload(form :  any , picture: Blob){
    const formData = new FormData();
    formData.set("title", form['title']);
    formData.set("comments", form['complain']);
    formData.set("picture", picture);
    formData.set("datetime", (new Date()).toString() )
    
    return firstValueFrom(this.httpClient.post<String>("/api/image/upload",formData));
  }

}
