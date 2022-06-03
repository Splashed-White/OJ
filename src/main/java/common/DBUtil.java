package common;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description: 封装数据库连接操作
 * User: X2148
 * Date: 2022-05-31
 * Time: 9:57
 */
public class DBUtil {
    // 需要封装和数据库之间的连接操作.
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/servlet_oj?characterEncoding=utf8&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "HAha214821854.";

    private static volatile DataSource dataSource = null;

    // 1. 创建数据源
    private static DataSource getDataSource() {
        if (dataSource == null) {
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setURL(URL);
            mysqlDataSource.setUser(USERNAME);
            mysqlDataSource.setPassword(PASSWORD);
            dataSource = mysqlDataSource;
        }
        return dataSource;
    }

    // 2. 创建连接
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    // 3. 释放资源
    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}