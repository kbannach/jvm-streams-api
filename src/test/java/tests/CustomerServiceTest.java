package tests;

import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import services.CustomerService;
import services.CustomerServiceInterface;
import data_produce.CustomerFunctions;
import data_produce.CustomerSampleFactory;
import data_produce.DataProducer;
import entities.Customer;

public class CustomerServiceTest {

   @Test
   public void testFindByName() {
      CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));

      String name = "Customer1";
      List<Customer> res = cs.findByName(name);

      Assertions.assertThat(res).isNotNull().hasSize(1).first().extracting(Customer::getName).contains(name);
   }

   @Test
   public void testFindByField() {
      String phoneNo = "123456789";
      CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(
            10,
            CustomerSampleFactory.getSample(1, CustomerFunctions.setPhoneNo, phoneNo),
            CustomerSampleFactory.getSample(2, CustomerFunctions.setPhoneNo, phoneNo)));

      List<Customer> res = cs.findByField("phoneNo", phoneNo);

      Assertions.assertThat(res).isNotNull().hasSize(2);
      Assertions.assertThat(res).allMatch(c -> c.getPhoneNo().equals(phoneNo));
   }

   @Test
   public void testCustomersWhoBoughtMoreThan() {
      CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(
            10,
            CustomerSampleFactory.getSample(1, 3, 15),
            CustomerSampleFactory.getSample(2, 2, 10),
            CustomerSampleFactory.getSample(3, 3, 12.5),
            CustomerSampleFactory.getSample(4, 4, 20),
            CustomerSampleFactory.getSample(5, 1, 5)

      ));

      List<Customer> res = cs.customersWhoBoughtMoreThan(3);
      Assertions.assertThat(res).isNotNull().hasSize(1).allMatch(c -> c.getId() == 4);

      res = cs.customersWhoBoughtMoreThan(2);
      Assertions.assertThat(res).isNotNull().hasSize(3);
      Assertions.assertThat(res.stream().map(c -> c.getId()).collect(Collectors.toList())).contains(1, 3, 4);
   }

   @Test
   public void testCustomersWhoSpentMoreThan() {
      CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(
            10,
            CustomerSampleFactory.getSample(1, 3, 15),
            CustomerSampleFactory.getSample(2, 2, 10),
            CustomerSampleFactory.getSample(3, 3, 12.5),
            CustomerSampleFactory.getSample(4, 4, 20),
            CustomerSampleFactory.getSample(5, 1, 5)

      ));

      List<Customer> res = cs.customersWhoSpentMoreThan(18);
      Assertions.assertThat(res).isNotNull().hasSize(1).allMatch(c -> c.getId() == 4);

      res = cs.customersWhoSpentMoreThan(14);
      Assertions.assertThat(res).isNotNull().hasSize(2);
      Assertions.assertThat(res.stream().map(c -> c.getId()).collect(Collectors.toList())).contains(1, 4);
   }

   @Test
   public void testCustomersWithNoOrders() {
      CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(
            5,
            CustomerSampleFactory.getSample(1, 3, 15),
            CustomerSampleFactory.getSample(2, 0, 0),
            CustomerSampleFactory.getSample(3, 3, 12.5),
            CustomerSampleFactory.getSample(4, 0, 0),
            CustomerSampleFactory.getSample(5, 0, 0)

      ));

      List<Customer> res = cs.customersWithNoOrders();

      Assertions.assertThat(res).isNotNull().hasSize(3);
      Assertions.assertThat(res.stream().map(c -> c.getId()).collect(Collectors.toList())).contains(2, 4, 5);
   }
   /*
   
   List<Customer> ();
   
   void addProductToAllCustomers(Product p);
   
   double avgOrders(boolean includeEmpty);
   
   boolean wasProductBought(Product p);
   
   List<Product> mostPopularProduct();
   
   int countBuys(Product p);
   
   int countCustomersWhoBought(Product p);
   
    */
}
