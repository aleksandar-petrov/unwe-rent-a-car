package bg.unwe.aleksandarpetrov.rentacar.constant;

public class AuthConstants {

    public static class JWT {
        public static final long EXPIRATION_TIME = 864_000_000; // 10 days

        public static final String HEADER_AUTHORIZATION = "Authorization";

        public static final String TOKEN_PREFIX = "Bearer ";
    }

    public static class Role {

        public static final String ROLE_USER = "ROLE_USER";

        public static final String ROLE_ADMIN = "ROLE_ADMIN";

        public static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    }

    private AuthConstants() {
    }
}
