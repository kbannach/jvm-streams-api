package tests;

import java.util.List;
import java.util.function.BiConsumer;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import services.CustomerService;
import services.CustomerServiceInterface;
import data_produce.CustomerMethodFactory;
import data_produce.DataProducer;
import entities.Customer;

public class CustomerServiceTest {

   private DataProducer dataProducer = new DataProducer();

   private SampleCustomization sample(int id, BiConsumer<Customer, Object> con, Object param) {
      return new SampleCustomization(id, con, param);
   }

   @Test
   public void testFindByName() {
      CustomerServiceInterface cs = new CustomerService(dataProducer.getTestData(10));

      String name = "Customer: 1";
      List<Customer> res = cs.findByName(name);

      Assertions.assertThat(res).isNotNull().hasSize(1).first().extracting(Customer::getName).contains(name);
   }

   @Test
   public void testfindByField() {
      String phoneNo = "123456789";
      CustomerServiceInterface cs = new CustomerService(dataProducer.getTestData(
            10,
            new SampleCustomization(1, CustomerMethodFactory.setPhoneNo(), phoneNo),
            new SampleCustomization(2, CustomerMethodFactory.setPhoneNo(), phoneNo)));

      List<Customer> res = cs.findByField("phoneNo", phoneNo);

      Assertions.assertThat(res).isNotNull().hasSize(2);
      Assertions.assertThat(res).allMatch(c -> c.getPhoneNo().equals(phoneNo));
   }
   /*
   
   List<Customer> findByField(String fieldName, Object value);

   List<Customer> customersWhoBoughtMoreThan(int number);
   
   List<Customer> customersWhoSpentMoreThan(double price);
   
   List<Customer> customersWithNoOrders();
   
   void addProductToAllCustomers(Product p);
   
   double avgOrders(boolean includeEmpty);
   
   boolean wasProductBought(Product p);
   
   List<Product> mostPopularProduct();
   
   int countBuys(Product p);
   
   int countCustomersWhoBought(Product p);
   
    */

}
