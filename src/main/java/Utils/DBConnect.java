package Utils;


import DataUser.Users;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DBConnect extends DBConfig {

    static Connection dbConnect;

    public static Connection getDbConnect() throws ClassNotFoundException, SQLException {

        String connectionDB = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("org.postgresql.Driver");
        dbConnect = DriverManager.getConnection(connectionDB, dbUser, dbPass);
        return dbConnect;
    }

    public void signAddUser(Users user) {
        try {
            PreparedStatement preparedStatement = getDbConnect().prepareStatement(RequestSQL.INSERT_USER_DB);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUseData(Users user) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = getDbConnect().prepareStatement(RequestSQL.SEARCH_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static void changeOfStatusOn(String login) throws SQLException, ClassNotFoundException {

        try {
            PreparedStatement preparedStatement = getDbConnect().prepareStatement(RequestSQL.CHANGE_STATUS_ON);
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void changeOfStatusOff(String login) throws SQLException, ClassNotFoundException {

        try {
            PreparedStatement preparedStatement = getDbConnect().prepareStatement(RequestSQL.CHANGE_STATUS_OFF);
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Set<String> getStatusList() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        Set<String> list = new HashSet<>();
        try {
            PreparedStatement preparedStatement = getDbConnect().prepareStatement(RequestSQL.GET_STATUS);
            resultSet = preparedStatement.executeQuery();

     while (resultSet.next()){
         String log = resultSet.getString(1);
         list.add(log);

     }



        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }return list;
    }
}
