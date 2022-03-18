package permissions;

public interface AuthenticationModule {

    boolean authenticate(AuthToken token);

    AuthToken login(String username, String password);

    void logout(AuthToken token);
}
