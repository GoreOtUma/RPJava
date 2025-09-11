import java.util.ArrayList;
import java.util.List;

public class RegAuthList implements UserDao {
    private List<MyUser> userList;

    public RegAuthList() {
        this.userList = new ArrayList<>();
    }

    @Override
    public boolean containsUser(String login) {
        for (MyUser user : userList) {
            if (user.getUserLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MyUser findUser(String login, String password) {
        for (MyUser user : userList) {
            if (user.getUserLogin().equals(login) && user.getUserPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void addUser(MyUser user) {
        userList.add(user);
    }
}