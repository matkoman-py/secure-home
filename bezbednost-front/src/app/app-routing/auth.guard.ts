import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    // this will be passed from the route config
    // on the data property
    const expectedRole = route.data['expectedRoles'];
    // decode the token to get its payload
    const tokenPayload = sessionStorage.getItem('role');

    if (sessionStorage.getItem('isLoggedIn') !== 'true') {
      this.router.navigate(['login']);
      return false;
    }
    if (!expectedRole.includes(tokenPayload)) {
      if (tokenPayload === 'ROLE_ADMIN') {
        this.router.navigate(['certificates']);
      } else {
        this.router.navigate(['generate-csr']);
      }
      return false;
    }
    return true;
  }
}
