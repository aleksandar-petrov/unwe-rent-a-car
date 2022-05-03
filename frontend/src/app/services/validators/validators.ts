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
} from 'rxjs';
import { map } from 'rxjs/operators';

export class RacValidators {
  static existingEmail(userService: UserService): AsyncValidatorFn {
    return this.checkIfAnyExists((email) => {
      return userService.anyUserExists({ email });
    }, 'emailAlreadyExists');
  }

  static existingPhoneNumber(userService: UserService): AsyncValidatorFn {
    return this.checkIfAnyExists((phoneNumber) => {
      return userService.anyUserExists({ phoneNumber });
    }, 'phoneNumberAlreadyExists');
  }

  private static checkIfAnyExists(
    booleanObservable: (value: string) => Observable<boolean>,
    errorName: string
  ): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return control.valueChanges
        .pipe(
          debounceTime(500),
          distinctUntilChanged(),
          switchMap(booleanObservable),
          map((result: boolean) => (result ? { [errorName]: true } : null))
        )
        .pipe(first());
    };
  }
}
