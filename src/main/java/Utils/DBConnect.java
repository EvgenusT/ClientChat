package Utils;


import DataUser.Users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DBConnect extends DBConfig {
    Connection dbConnect;

    public Connection getDbConnect() throws ClassNotFoundException, SQLException {
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
}
