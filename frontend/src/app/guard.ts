import { inject } from "@angular/core";
import { ActivatedRoute, ActivatedRouteSnapshot, CanActivateFn, CanDeactivateFn, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { PictureComponent } from "./views/picture.component";

//only activate this when back not leaving*
  export const leaveMainPage: CanDeactivateFn<PictureComponent> =
  (comp: PictureComponent, route: ActivatedRouteSnapshot, state: RouterStateSnapshot)
      : boolean | UrlTree | Promise<boolean | UrlTree> | Observable<boolean | UrlTree>  => {

    const router = inject(Router)

    
    if (!comp.isPictureSaved()) {
      //console.log("Are you here 111?")
      return confirm('Are you sure you want to discard image?')
    }
    //console.log("Are you here 222?")

    //return router.parseUrl('/')
    return true
  }