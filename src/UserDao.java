import java.util.List;

public interface UserDao {
    boolean containsUser(String login);
    MyUser findUser(String login, String password);
    void addUser(MyUser user);
}