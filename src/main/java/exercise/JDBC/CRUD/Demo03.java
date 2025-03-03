package exercise.JDBC.CRUD;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * JDBC的CRUD
 */
public class Demo03 {
    private Connection conn = null;
    private Statement stat = null;
    private ResultSet rs = null;

    @Before
    public void before() {
        try {
            //1.注册数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.获取数据库连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/day16?serverTimezone=UTC", "root", "abc123");
            //3.获取传输器
            stat = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @After
    public void after() {
        //6.关闭资源
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                rs = null;
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                stat = null;
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                stat = null;
            }
        }
    }

    /**
     * 查询
     */
    @Test
    public void query() {
        try {
            //1.传输sql执行,获取结果
            rs = stat.executeQuery("select * from user where id <=2");
            //2.处理结果
            while (rs.next()) {
                String name = rs.getString("name");
                System.out.println(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除
     */
    @Test
    public void delete() {
        try {
            //1.传输sql执行,获取结果
            int i = stat.executeUpdate("delete from user where id = 4");
            //2.处理就结果
            if (i <= 0) {
                System.out.println("删除失败!");
            } else {
                System.out.println("删除成功!影响的行数为:" + i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改
     */
    @Test
    public void update() {
        try {
            //1.传输sql执行,获取结果
            int i = stat.executeUpdate("update user set age=88 where id=4");
            //2.处理结果
            if (i <= 0) {
                System.out.println("执行失败!");
            } else {
                System.out.println("修改数据成功!影响到的行数为" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 新增
     */
    @Test
    public void insert() {
        try {
            //1.传输sql执行获取结果集
            int i = stat.executeUpdate("insert into user values (4,'ddd',33)");
            //2.处理结果
            if (i <= 0) {
                System.out.println("插入失败!");
            } else {
                System.out.println("插入成功，影响到的行数为" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Demo03 demo03 = new Demo03();
        demo03.before();
        demo03.insert();
        demo03.query();
        demo03.delete();
        demo03.after();
    }
}
