package Utils;

public class RequestSQL {
    public static final String INSERT_USER_DB = "INSERT INTO " + CONST.USER_TABLE +
            "(" + CONST.LOGIN + ", " + CONST.PASSWORD + ") " + "VALUES (?,?)";

    public static final String SEARCH_USER = "SELECT * FROM " + CONST.USER_TABLE +
            " WHERE " + CONST.LOGIN + " =? AND " + CONST.PASSWORD + "=?";
}
