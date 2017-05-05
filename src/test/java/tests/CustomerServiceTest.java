package tests;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import services.CustomerService;
import services.CustomerServiceInterface;
import data_produce.CustomerFunctions;
import data_produce.CustomerSampleFactory;
import data_produce.DataProducer;
import entities.Customer;

public class CustomerServiceTest {

   private DataProducer dataProducer = new DataProducer();

   @Test
   public void testFindByName() {
      CustomerServiceInterface cs = new CustomerService(dataProducer.getTestData(10));

      String name = "Customer: 1";
      List<Customer> res = cs.findByName(name);

      Assertions.assertThat(res).isNotNull().hasSize(1).first().extracting(Customer::getName).contains(name);
   }

   @Test
   public void testFindByField() {
      String phoneNo = "123456789";
      CustomerServiceInterface cs = new CustomerService(dataProducer.getTestData(
            10,

            CustomerSampleFactory.getSample(1, CustomerFunctions.setPhoneNo, phoneNo),
            CustomerSampleFactory.getSample(1, CustomerFunctions.setEmail, "test"),
            CustomerSampleFactory.getSample(1, CustomerFunctions.setTaxId, "1"),

            CustomerSampleFactory.getSample(2, CustomerFunctions.setPhoneNo, phoneNo),
            CustomerSampleFactory.getSample(2, CustomerFunctions.setEmail, "test2"),
            CustomerSampleFactory.getSample(2, CustomerFunctions.setTaxId, "2")

      ));

      List<Customer> res = cs.findByField("phoneNo", phoneNo);

      Assertions.assertThat(res).isNotNull().hasSize(2);
      Assertions.assertThat(res).allMatch(c -> c.getPhoneNo().equals(phoneNo));
   }

   /*

   testCustomersWhoBoughtMoreThan
   
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
