package exercise.annotation.dao;

import jdk.jfr.Description;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@MyDBMS
public class MysqlDao implements MyDao {
    @Override
    public void add() {
        System.out.println("mysql add");
    }

    @Override
    public void get() {
        System.out.println("mysql get");
    }
}