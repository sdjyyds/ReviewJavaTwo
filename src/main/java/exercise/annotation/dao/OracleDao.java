package exercise.annotation.dao;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public class OracleDao implements MyDao {
    @Override
    public void add() {
        System.out.println("oracle add");
    }

    @Override
    public void get() {
        System.out.println("oracle get");
    }
}
