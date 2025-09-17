import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class UserDaoImplFile implements UserDao {
    private String filename;
    private AtomicLong idCounter;

    public UserDaoImplFile(String filename) {
        this.filename = filename;
        ensureFileExists();
        this.idCounter = new AtomicLong(getMaxId() + 1);
    }

    private void ensureFileExists() {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Создан новый файл для хранения данных: " + filename);
                }
            } catch (IOException e) {
                System.out.println("Ошибка при создании файла: " + e.getMessage());
            }
        }
    }

    private long getMaxId() {
        List<MyUser> users = getAll();
        return users.stream().mapToLong(MyUser::getId).max().orElse(0);
    }

    private void saveUsers(List<MyUser> users) {
        File file = new File(filename);

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (MyUser user : users) {
                writer.println(user.toString());
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    @Override
    public Optional<MyUser> get(long id) {
        return getAll().stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    @Override
    public List<MyUser> getAll() {
        List<MyUser> users = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists() || file.length() == 0) {
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    MyUser user = MyUser.fromString(line);
                    if (user != null) {
                        users.add(user);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void save(MyUser user) {
        if (user.getId() == 0) {
            user.setId(idCounter.getAndIncrement());
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(user.toString()); // ← Добавляем только нового пользователя
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    @Override
    public void update(MyUser user, String[] params) {
        if (params.length >= 2) {
            user.setLogin(params[0]);
            user.setPassword(params[1]);

            List<MyUser> users = getAll();
            users.removeIf(u -> u.getId() == user.getId());
            users.add(user);
            saveUsers(users);
        }
    }

    @Override
    public void delete(MyUser user) {
        List<MyUser> users = getAll();
        users.removeIf(u -> u.getId() == user.getId());
        saveUsers(users);
    }

    @Override
    public Optional<MyUser> findByLogin(String login) {
        return getAll().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }

    @Override
    public Optional<MyUser> findByLoginAndPassword(String login, String password) {
        return getAll().stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findFirst();
    }

    @Override
    public boolean existsByLogin(String login) {
        return findByLogin(login).isPresent();
    }
}