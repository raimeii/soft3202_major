package permissions;

public interface AuthorisationModule {

    boolean authorise(AuthToken token, boolean secure);

}
