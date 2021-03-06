package bg.unwe.aleksandarpetrov.rentacar.constant;

public class ErrorConstants {

  public static final String USER_NOT_FOUND = "User with email %s not found.";

  public static final String INVALID_USER = "Invalid user with id - %s.";

  public static final String DUPLICATE_EMAIL = "Email %s already registered in the system.";

  public static final String DUPLICATE_PHONE_NUMBER =
      "Phone number %s already registered in the system.";

  public static final String PASSWORD_MISMATCH = "Password doesn't match with confirm password.";

  public static final String INVALID_PHOTO = "Invalid photo with id - %s.";

  public static final String INVALID_CAR = "Invalid car with id - %s.";

  public static final String LOCKED_CAR = "Car with id - %s is locked.";

  public static final String INVALID_RENTAL_RENTER = "Invalid rental with id - %s and renter id %s.";

  public static final String INVALID_RENTAL_OWNER = "Invalid rental with id - %s and owner id %s.";

  private ErrorConstants() {}
}
