import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RegAuthFile implements UserDao {
    private String filename;

    public RegAuthFile(String filename) {
        this.filename = filename;
        ensureFileExists();
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

    private List<MyUser> loadUsers() {
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
    public boolean containsUser(String login) {
        List<MyUser> users = loadUsers();
        for (MyUser user : users) {
            if (user.getUserLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MyUser findUser(String login, String password) {
        List<MyUser> users = loadUsers();
        for (MyUser user : users) {
            if (user.getUserLogin().equals(login) && user.getUserPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void addUser(MyUser newUser) {
        List<MyUser> users = loadUsers();
        users.add(newUser);
        saveUsers(users);
    }

    public String getFileInfo() {
        File file = new File(filename);
        if (file.exists()) {
            return "Файл: " + filename + " | Размер: " + file.length() + " байт";
        }
        return "Файл не существует: " + filename;
    }
}