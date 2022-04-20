import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { UserService } from '../services/user.service';
import { Observable } from 'rxjs';
import { map, take } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanActivate {
  constructor(private userService: UserService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    router: RouterStateSnapshot
  ):
    | boolean
    | UrlTree
    | Promise<boolean | UrlTree>
    | Observable<boolean | UrlTree> {
    return this.userService.user$.pipe(
      take(1),
      map((user) => {
        if (user?.roles.includes('ROLE_ADMIN')) {
          return true;
        }

        return this.router.createUrlTree(['/login']);
      })
    );
  }
}
