package understand;

import com.mysql.jdbc.Driver;
import exercise.JDBC.connection.pool.Mypool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * <p>
 * JDBC
 * 概念
 * 数据库驱动
 * 数据库厂商提供的用来操作数据库的jar包就叫做数据库的驱动，不同的厂商提供的驱动包不通用
 * JDBC（Java DataBase Connectivity）就是Java数据库连接，是SUN公司提供的连接和操作数据库的技术
 * 说白了就是用Java语言来操作数据库的技术。
 * 由于不同的数据库厂商提供的数据库驱动各不相同,在使用不同数据库时需要学习对应数据库驱动的
 * API，对于开发人员来说学习成本十分的高。
 * 于是sun提供了JDBC的规范，本质上一大堆的接口，要求不同的数据库厂商提供的驱动都实现这套接口，这样一来
 * 开发人员只需要学会JDBC这套接口，所有的数据库驱动作为这套接口的实现，就都会使用了。大大降低了学习成本。
 * <p>
 * JDBC包
 * 主要是由java.sql和javax.sql包组成的,并且这两个包已经被集成到J2SE的规范当中了,这意味着,只要一个普通
 * 的J2SE程序就可以使用JDBC了
 * 要注意的是,在开发数据库程序时,出来如上两个包,还需手动导入具体的数据库驱动
 * <p>
 * Connection
 * Connection代表数据库的连接,是数据库编程中最重要的一个对象
 * 客户端与数据库所有交互都是通过connection对象完成的
 * API
 * createStatement()//创建向数据库发送sql的statement对象。
 * prepareStatement(sql)//创建向数据库发送预编译sql的PreparedSatement对象。
 * setAutoCommit(boolean autoCommit)//设置事务是否自动提交。
 * commit()//在链接上提交事务。
 * rollback()//在此链接上回滚事务。
 * <p>
 * Statement
 * Statement对象用于向数据库发送SQL语句,并获取执行结果
 * API
 * ResultSet executeQuery(String sql) //用于向数据库发送查询语句。
 * int executeUpdate(String sql) //用于向数据库发送insert、update或delete语句
 * boolean execute(String sql) //用于向数据库发送任意sql语句
 * addBatch(String sql) //把多条sql语句放到一个批处理中
 * executeBatch() //向数据库发送一批sql语句执行
 * <p>
 * ResultSet
 * ResultSet对象代表SQL查询结果
 * 其中以表的形式封装了查询结果数据
 * API
 * boolean next() //移动到下一行,如果成功指向了一条新的数据返回true，否则返回false
 * boolean Previous() //移动到前一行,如果成功指向了一条数据返回true，否则返回false
 * boolean absolute(int row) //移动到指定行,如果成功指向了一条数据返回true，否则返回false
 * void beforeFirst()//移动resultSet的第一行的前面
 * void afterLast() //移动到resultSet的最后一行的后面
 * String getString(int index)
 * String getString(String columnName)
 * int getInt(columnIndex)
 * int getInt(columnLabel)
 * double getDouble(columnIndex)
 * double getDouble(columnLabel)
 * ...
 * Object getObject(int index)
 * Object getObject(string columnName)
 * <p>
 * 释放资源
 * JDBC相关的对象使用之后都要释放
 * Connection对象占用数据库连接，而数据连接非常有限且宝贵，使用过后要尽快释放
 * Statement对象、ResultSet对象中封装着SQL语句、执行结果数据，占用内存资源，用过后也应尽快释放
 * 释放顺序：越晚获取的对象越先关闭
 *
 * SQL注入攻击
 * 通过SQL语句注入的攻击是一种常见的攻击(见demo04,demo05)
 *
 * 连接池
 * 数据库连接是一个非常宝贵的资源,同时也是一种非常沉重的资源，开关连接都需要耗费大量的时间和资源
 * 如果每次访问数据库都重新创建、销毁连接
 * 则网站每天10w访问量，就意味着创建10w次连接关闭10w次连接，会极大耗费数据库资源
 * 甚至有可能造成数据库服务器内存溢出、宕机
 * 频繁的开关连接相当的耗费资源
 * 所以我们可以设置一个连接池，在程序启动时就初始化一批连接放到池中在程序里共享
 * 需要连接时从池中获取，用完连接后不要关闭而是还回池中
 * 通过池实现了连接的共享，减少了连接的创建和销毁，减少了资源的消耗，提供了程序的效率(可见图解)
 * 手写连接池
 * Sun公司为连接池提供 javax.sql.DataSource接口，要求连接池去实现，所以连接池也叫数据源。
 *
 */
public class LearnFourteenth {
    public static void main(String[] args) {
        Mypool mypool = null;
        Connection conn = null;
        Statement stat = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //1.注册数据库驱动
        //DriverManager.registerDriver(new Driver());
        try {
            //1.初始化连接池
            mypool = Mypool.getDataSource();
            //2.获取连接对象
            conn = mypool.getConnection();
            //3.获取传输器
            stat = conn.createStatement();
            //4.传输sql执行获取结果集
            rs = stat.executeQuery("select * from user");
            //5.处理结果
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println(id + "#" + name + "#" + age);
            }
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("update user set name=? where id=?");
            ps.setString(1, "小红");
            ps.setInt(2, 1);
            ps.execute();
            //conn.commit();
            ps = conn.prepareStatement("select * from user where id=?");
            ps.setInt(1, 1);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println(id + "#" + name + "#" + age);
            }
            conn.rollback();
            //再查询一次
            ps = conn.prepareStatement("select * from user where id=?");
            ps.setInt(1, 1);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println(id + "#" + name + "#" + age);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            mypool.retConn(conn);
            mypool = null;
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    rs = null;
                    if (stat != null) {
                        try {
                            stat.close();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } finally {
                            stat = null;
                            try {
                                ps.close();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            } finally {
                                ps = null;
                            }
                        }
                    }
                }
            }
        }
    }
}

