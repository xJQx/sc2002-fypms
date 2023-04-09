package interfaces;

public interface IAuthService {
    public boolean login(String userID, String password);

    public boolean logout();
}
