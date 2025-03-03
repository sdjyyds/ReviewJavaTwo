package exercise.JDBC.SqlAttack;

import java.sql.*;
import java.util.Scanner;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * solve SQL attack
 */
public class Demo05 {
    public static void main(String[] args) throws SQLException {
        Scanner scan = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String name = null;
        String password = null;
        try {
            scan = new Scanner(System.in);
            //1.注册mysql驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.获取Connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/day16?serverTimezone=UTC", "root", "abc123");
            while (true) {
                //3.获取PreparedStatement
                ps = conn.prepareStatement("select * from user2 where username=? and password=?");
                //4.获取用户输入
                System.out.println("请输入用户名:");
                name = scan.nextLine();
                System.out.println("请输入密码:");
                password = scan.nextLine();
                //5.设置参数
                ps.setString(1, name);
                ps.setString(2, password);
                //6.执行sql
                rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("登录成功!");
                    break;
                } else {
                    System.out.println("登录失败!");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        } finally {
            name = password = null;
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e);
                } finally {
                    rs = null;
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.out.println(e);
                } finally {
                    ps = null;
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e);
                } finally {
                    conn = null;
                }
            }
        }
    }
}
