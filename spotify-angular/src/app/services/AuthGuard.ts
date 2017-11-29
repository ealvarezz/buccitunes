import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import {AuthenticationService} from './authentication.service'
 
@Injectable()
export class AuthGuard implements CanActivate {
 
    constructor(private router: Router, private authenticationService: AuthenticationService) { }
 
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if(state.url === '/login' && this.authenticationService.getLoggedInUser()){
            return false;
        }
        if (this.authenticationService.getLoggedInUser()) {
            return true;
        }
 
        // not logged in so redirect to login page with the return url
        this.router.navigate(['/login'], { queryParams: { returnUrl: state.url }});
        return false;
    }
}