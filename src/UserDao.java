import java.util.Optional;

public interface UserDao extends Dao<MyUser> {
    Optional<MyUser> findByLogin(String login);
    Optional<MyUser> findByLoginAndPassword(String login, String password);
    boolean existsByLogin(String login);
}