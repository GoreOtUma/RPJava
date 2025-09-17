import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class UserDaoImplList implements UserDao {
    private List<MyUser> userList;
    private AtomicLong idCounter;

    public UserDaoImplList() {
        this.userList = new ArrayList<>();
        this.idCounter = new AtomicLong(1);
    }

    @Override
    public Optional<MyUser> get(long id) {
        return userList.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    @Override
    public List<MyUser> getAll() {
        return new ArrayList<>(userList);
    }

    @Override
    public void save(MyUser user) {
        if (user.getId() == 0) {
            user.setId(idCounter.getAndIncrement());
        }
        userList.add(user);
    }

    @Override
    public void update(MyUser user, String[] params) {
        if (params.length >= 2) {
            user.setLogin(params[0]);
            user.setPassword(params[1]);

            userList.removeIf(u -> u.getId() == user.getId());
            userList.add(user);
        }
    }

    @Override
    public void delete(MyUser user) {
        userList.removeIf(u -> u.getId() == user.getId());
    }

    @Override
    public Optional<MyUser> findByLogin(String login) {
        return userList.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }

    @Override
    public Optional<MyUser> findByLoginAndPassword(String login, String password) {
        return userList.stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findFirst();
    }

    @Override
    public boolean existsByLogin(String login) {
        return findByLogin(login).isPresent();
    }

    public String getStorageInfo() {
        return "Список пользователей: " + userList.size();
    }
}