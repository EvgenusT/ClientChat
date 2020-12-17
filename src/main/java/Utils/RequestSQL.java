package Utils;

public class RequestSQL {
    public static final String INSERT_USER_DB = "INSERT INTO " + CONST.USER_TABLE +
            "(" + CONST.LOGIN + ", " + CONST.PASSWORD + ", " + CONST.STATUS + ") " + "VALUES (?,?,0)";

    public static final String SEARCH_USER = "SELECT * FROM " + CONST.USER_TABLE +
            " WHERE " + CONST.LOGIN + " =? AND " + CONST.PASSWORD + "=?";

    public static final String CHANGE_STATUS_ON = "UPDATE " + CONST.USER_TABLE +
            " SET " + CONST.STATUS + " = 1 " + "WHERE " + CONST.LOGIN + " = ?";

    public static final String CHANGE_STATUS_OFF = "UPDATE " + CONST.USER_TABLE +
            " SET " + CONST.STATUS + " = 0 " + "WHERE " + CONST.LOGIN + " = ?";


    public static final String GET_STATUS = "SELECT " + CONST.LOGIN + " FROM " + CONST.USER_TABLE +
            " WHERE " + CONST.STATUS + " = " + 1;
}


