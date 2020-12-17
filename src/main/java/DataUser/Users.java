package DataUser;

public class Users {

    static private String login;
    static private String password;

    static Users user = null;

    public Users() {
        user = this;
    }

    public Users(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public static String getLogin() {
        return login;
    }

    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
