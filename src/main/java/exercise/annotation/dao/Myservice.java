package exercise.annotation.dao;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public class Myservice {
    //private MyDao myDao = MyDaoFactory.createMyDao();
    private MyDao myDao = MyDaoFactory.getDao();
    public void test() {
        myDao.add();
        myDao.get();
    }

    public static void main(String[] args) {
        Myservice myservice = new Myservice();
        myservice.test();
    }
}
