import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserDao userDao = new UserDaoImplFile("users.txt");
        System.out.println("Используется файловое хранилище данных");
        //UserDao userDao = new UserDaoImplList();
        //System.out.println("Данные сохраняются в список");

        AppRegAuth app = new AppRegAuth(userDao);

        boolean running = true;

        while (running) {
            System.out.println("1. Регистрация");
            System.out.println("2. Авторизация");
            System.out.println("3. Выход");
            System.out.print("Выберите действие: ");

            int choice = getIntInput(scanner);
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleRegistration(scanner, app);
                    break;

                case 2:
                    handleAuthorization(scanner, app);
                    break;

                case 3:
                    running = false;
                    System.out.println("До свидания! Данные сохранены в файле.");
                    break;

                default:
                    System.out.println("Неверный выбор!");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static int getIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Пожалуйста, введите число!");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void handleRegistration(Scanner scanner, AppRegAuth app) {
        System.out.print("Введите логин: ");
        String login = scanner.nextLine().trim();
        if (login.isEmpty()) {
            System.out.println("Логин не может быть пустым!");
            return;
        }

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println("Пароль не может быть пустым!");
            return;
        }

        app.register(login, password);
    }

    private static void handleAuthorization(Scanner scanner, AppRegAuth app) {
        System.out.print("Введите логин: ");
        String login = scanner.nextLine().trim();
        if (login.isEmpty()) {
            System.out.println("Логин не может быть пустым!");
            return;
        }

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println("Пароль не может быть пустым!");
            return;
        }

        app.auth(login, password);
    }
}