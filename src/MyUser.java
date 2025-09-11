public class MyUser {
    private String login;
    private String password;

    public MyUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getUserLogin() { return login; }
    public void setUserLogin(String username) { this.login = login; }

    public String getUserPassword() { return password; }
    public void setUserPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return login + ":" + password;
    }

    public static MyUser fromString(String userString) {
        if (userString == null || userString.trim().isEmpty()) {
            return null;
        }

        String[] parts = userString.split(":");
        if (parts.length == 2) {
            String login = parts[0].trim();
            String password = parts[1].trim();
            if (!login.isEmpty() && !password.isEmpty()) {
                return new MyUser(login, password);
            }
        }
        return null;
    }
}
