import { UserService } from '../user.service';
import {
  AbstractControl,
  AsyncValidatorFn,
  ValidationErrors,
} from '@angular/forms';
import {
  debounceTime,
  distinctUntilChanged,
  first,
  Observable,
  switchMap,
  tap,
} from 'rxjs';
import { map } from 'rxjs/operators';

export class ExistingEmailValidator {
  static createValidator(userService: UserService): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return control.valueChanges
        .pipe(
          debounceTime(500),
          distinctUntilChanged(),
          switchMap((email) => userService.anyUserExists(email)),
          tap(console.log),
          map((result: boolean) =>
            result ? { emailAlreadyExists: true } : null
          )
        )
        .pipe(first());
    };
  }
}
