package TestSqlDao;

public class main {

    public static void main(String[] args) throws Exception {
        TestSqlDao test = TestSqlDao.getInstance();

        System.out.println(test.getMaxUserOrderId(1));

       // test.copyUserOrders(1,3);

        System.out.println(test.getUserMaxOrder(2));
    }

}
