import java.util.List;

public class AppRegAuth {
    private UserDao userDao;

    public AppRegAuth(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean register(String login, String password) {
        if (userDao.containsUser(login)) {
            System.out.println("Пользователь с таким логином уже существует!");
            return false;
        }

        MyUser newUser = new MyUser(login, password);
        userDao.addUser(newUser);
        System.out.println("Регистрация прошла успешно!");
        return true;
    }

    public boolean auth(String login, String password) {
        MyUser user = userDao.findUser(login, password);
        if (user != null) {
            System.out.println("Авторизация прошла успешно!");
            return true;
        }

        System.out.println("Неверный логин или пароль!");
        return false;
    }
}