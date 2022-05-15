import { Injectable } from '@angular/core';
import {
  BehaviorSubject,
  lastValueFrom,
  Observable,
  skipWhile,
  tap,
} from 'rxjs';
import {
  AccessToken,
  AnyUserExistsRequest,
  Role,
  User,
} from '../models/user.model';
import { JwtHelperService } from '@auth0/angular-jwt';
import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
} from '@angular/common/http';
import { environment } from '../../environments/environment';
import { LoginRequest, LoginResponse } from '../models/login.model';
import { ToastrService } from 'ngx-toastr';
import { RegisterRequest, RegisterResponse } from '../models/register.model';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';

const ACCESS_TOKEN = 'access_token';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  static TOKEN: string | null;
  private readonly userSubject$ = new BehaviorSubject<User | null>(null);
  userId$ = this.userSubject$.pipe(
    skipWhile((user) => user === null),
    map((user) => user!.id)
  );
  user$ = this.userSubject$.asObservable();
  private readonly anyUserExistsSubject$ = new BehaviorSubject<boolean>(true);
  anyUserExists$ = this.anyUserExistsSubject$.asObservable();

  constructor(
    private jwtService: JwtHelperService,
    private http: HttpClient,
    private toastrService: ToastrService,
    private router: Router
  ) {}

  async autoLogin(): Promise<void> {
    const token = localStorage.getItem(ACCESS_TOKEN);
    if (!token) {
      return;
    }

    if (this.jwtService.isTokenExpired(token)) {
      localStorage.removeItem(ACCESS_TOKEN);
      return;
    }

    this.updateUser(token);
  }

  async login(req: LoginRequest, rememberMe: boolean): Promise<void> {
    try {
      const response = await lastValueFrom(
        this.http.post<LoginResponse>(`${environment.API_URL}/login`, req)
      );

      if (rememberMe) {
        localStorage.setItem(ACCESS_TOKEN, response.access_token);
      }

      this.updateUser(response.access_token);
    } catch (e) {
      const err = e as HttpErrorResponse;
      if (err?.status !== 401) {
        return;
      }

      this.toastrService.error('Incorrect email or password!');
    }
  }

  register(req: RegisterRequest): Observable<RegisterResponse> {
    return this.http
      .post<RegisterResponse>(`${environment.API_URL}/users`, req)
      .pipe(
        tap(() => {
          this.anyUserExistsSubject$.next(true);
        })
      );
  }

  anyUserExists(model: AnyUserExistsRequest = {}): Observable<boolean> {
    let params = new HttpParams();
    if (model.email) {
      params = params.append('email', model.email);
    }
    if (model.phoneNumber) {
      params = params.append(
        'phoneNumber',
        model.phoneNumber.replace('+', '%2B')
      );
    }

    return this.http.get<boolean>(`${environment.API_URL}/users/any`, {
      params,
    });
  }

  logout() {
    this.userSubject$.next(null);
    localStorage.removeItem(ACCESS_TOKEN);
    UserService.TOKEN = null;

    this.router.navigate(['/']);
  }

  initialize() {
    this.autoLogin();
    this.anyUserExists().subscribe((anyUserExists) => {
      this.anyUserExistsSubject$.next(anyUserExists);

      if (!anyUserExists) {
        this.router.navigateByUrl('/initialize');
      }
    });
  }

  private updateUser(token: string) {
    const accessToken = this.jwtService.decodeToken<AccessToken>(token);

    this.userSubject$.next({
      id: accessToken.userId,
      email: accessToken.sub,
      roles: accessToken.roles
        .split(',')
        .map((r) => Role[r as keyof typeof Role]),
      token: token,
    });

    UserService.TOKEN = token;
  }
}
