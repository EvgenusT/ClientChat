package DataUserTest;

import DataUser.Users;
import Utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersTest {

    Users users;

    @BeforeEach
    public void setUp() throws Exception {
        users = new Users();
    }

    @Test
    public void setLoginTest() throws Exception {
        String login = "Profil";
        users.setLogin(login);
        assertEquals(users.getLogin(), login);
    }

    @Test
    public void setPasswordTest() throws Exception {
        String password = "123456";
        users.setPassword(password);
        assertEquals(users.getPassword(), password);
    }

}
