import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import {AuthenticationService} from './authentication.service'
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
 
@Injectable()
export class AuthGuard implements CanActivate {
 
    constructor(private router: Router, private authenticationService: AuthenticationService) { }
 
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) :Observable<boolean>|boolean {

        return this.authenticationService.getLoggedInUser()
        .map(user =>{
            if(state.url != '/login'){
                return true;
            }
            else{
                return false;
            }
        })
        .catch(err=>{
            this.router.navigate(['/login'], { queryParams: { returnUrl: state.url }});
            return Observable.of(false);
        })

    }
}