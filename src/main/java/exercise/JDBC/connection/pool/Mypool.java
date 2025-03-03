package exercise.JDBC.connection.pool;

import com.mysql.cj.jdbc.MysqlDataSource;
import tool.ReadFile;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public class Mypool implements DataSource {
    private static List<Connection> pool;
    private static Mypool myDatabase;
    static {
        pool = new ArrayList();
        Collections.synchronizedList(pool);
        //1.注册驱动
        try {
            Class.forName(ReadFile.getValue("jdbc.driver"));
            for (int i = 0; i < 5; i++) {
                Connection conn = DriverManager.getConnection(ReadFile.getValue("jdbc.url"), ReadFile.getValue("jdbc.username"), ReadFile.getValue("jdbc.password"));
                pool.add(conn);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        myDatabase = new Mypool();
    }
    public static Mypool getDataSource(){
        return myDatabase;
    }
    @Override
    public Connection getConnection() throws SQLException {
        //如果池里不够了，再存进去3个
        if (pool.size() <= 0) {
            for (int i = 0; i < 3; i++) {
                Connection conn =
                        DriverManager.getConnection(ReadFile.getValue("jdbc.url"), ReadFile.getValue("jdbc.username"), ReadFile.getValue("jdbc.password"));
                pool.add(conn);
            }
        }
        //从池中取出一个连接返回
        return pool.remove(0);
    }

    /**
     * 还连接的方法
     */
    public void retConn(Connection conn) {
        try {
            //要求连接不为null 且没有关闭过 ，将连接存入池中
            if (conn != null && !conn.isClosed()) {
                pool.add(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
