package bg.unwe.aleksandarpetrov.rentacar.constant;

public class ErrorConstants {

  public static final String USER_NOT_FOUND = "User with email %s not found.";

  public static final String INVALID_USER = "Invalid user id %s.";

  public static final String DUPLICATE_EMAIL = "Email %s already registered in the system.";

  public static final String PASSWORD_MISMATCH = "Password doesn't match with confirm password.";

  public static final String ACTIVE_TOKEN = "There is pending active reset token. Try later.";

  public static final String INVALID_ROLE = "Invalid role %s.";

  public static final String INVALID_SORT_DIRECTION = "Invalid sort parameters %s -> %s";

  private ErrorConstants() {}
}
