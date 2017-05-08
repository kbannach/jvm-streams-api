package tests;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import services.CustomerService;
import services.CustomerServiceInterface;
import data_produce.CustomerFunctions;
import data_produce.CustomerSampleFactory;
import data_produce.DataProducer;
import entities.Customer;
import entities.Product;

public class CustomerServiceTest {

   @Test
   public void testFindByName() {
      CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));

      String name = "Customer1";
      List<Customer> res = cs.findByName(name);

      Assertions.assertThat(res).isNotNull().hasSize(1).first().extracting(Customer::getName).contains(name);
   }

   @Test
   public void testFindByFieldPhoneNo() {
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
   public void testFindByFieldEmail() {
      String email = "foo@bar.org";
      CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(
            10,
            CustomerSampleFactory.getSample(1, CustomerFunctions.setEmail, email),
            CustomerSampleFactory.getSample(2, CustomerFunctions.setEmail, "testtest"),
            CustomerSampleFactory.getSample(3, CustomerFunctions.setEmail, email)));

      List<Customer> res = cs.findByField("email", email);

      Assertions.assertThat(res).isNotNull().hasSize(2);
      Assertions.assertThat(res).allMatch(c -> c.getEmail().equals(email));
   }

   @Test
   public void testFindByFieldTaxId() {
      String taxId = "testTaxId";
      CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(
            10,
            CustomerSampleFactory.getSample(1, CustomerFunctions.setTaxId, taxId),
            CustomerSampleFactory.getSample(2, CustomerFunctions.setTaxId, taxId),
            CustomerSampleFactory.getSample(2, CustomerFunctions.setEmail, "testEmail"),
            CustomerSampleFactory.getSample(3, CustomerFunctions.setTaxId, "nvkadsl")));

      List<Customer> res = cs.findByField("taxId", taxId);

      Assertions.assertThat(res).isNotNull().hasSize(2);
      Assertions.assertThat(res).allMatch(c -> c.getTaxId().equals(taxId));
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

   @Test
   public void testAddProductToAllCustomers() {
      List<Customer> customers = DataProducer.getTestData(5);
      CustomerServiceInterface cs = new CustomerService(customers);
      Product newProduct = new Product(9001, "Over 9000!!!", 9000.1);

      cs.addProductToAllCustomers(newProduct);

      Assertions.assertThat(customers).allMatch(c -> c.getBoughtProducts().contains(newProduct));
   }

   @Test
   public void testAvgOrders() {
      CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(
            5,
            CustomerSampleFactory.getSample(1, 1, 15),
            CustomerSampleFactory.getSample(2, 2, 10),
            CustomerSampleFactory.getSample(3, 1, 12.5),
            CustomerSampleFactory.getSample(4, 1, 0),
            CustomerSampleFactory.getSample(5, 0, 0)

      ));

      double res = cs.avgOrders(false);
      Assertions.assertThat(res).isEqualByComparingTo(37.5 / 4);

      res = cs.avgOrders(true);
      Assertions.assertThat(res).isEqualByComparingTo(37.5 / 5);
   }

   @Test
   public void testWasProductBoughtNone() {
      List<Customer> customers = DataProducer.getTestData(5);
      CustomerServiceInterface cs = new CustomerService(customers);

      Product newProduct = new Product(9001, "Over 9000!!!", 9000.1);
      boolean ret = cs.wasProductBought(newProduct);

      Assertions.assertThat(ret).isFalse();
   }

   @Test
   public void testWasProductBoughtAll() {
      List<Customer> customers = DataProducer.getTestData(5);
      CustomerServiceInterface cs = new CustomerService(customers);

      Product newProduct = new Product(9001, "Over 9000!!!", 9000.1);
      cs.addProductToAllCustomers(newProduct);
      boolean ret = cs.wasProductBought(newProduct);

      Assertions.assertThat(ret).isTrue();
   }

   @Test
   public void testWasProductBoughtOne() {
      List<Customer> customers = DataProducer.getTestData(5);
      CustomerServiceInterface cs = new CustomerService(customers);
      Product newProduct = new Product(9001, "Over 9000!!!", 9000.1);

      customers.get(new Random().nextInt(customers.size())).getBoughtProducts().add(newProduct);
      boolean ret = cs.wasProductBought(newProduct);

      Assertions.assertThat(ret).isTrue();
   }

   @Test
   public void testMostPopularProduct() {
      List<Customer> customers = DataProducer.getTestData(10, CustomerSampleFactory.getSample(1, 2, 5), CustomerSampleFactory.getSample(1, 4, 50), CustomerSampleFactory.getSample(1, 1, 13));
      CustomerServiceInterface cs = new CustomerService(customers);

      Product newProduct = new Product(9001, "Over 9000!!!", 9000.1);
      IntStream.range(0, 5).forEach(i -> customers.get(i).addProduct(newProduct));
      Product newProduct2 = new Product(800, "Terminator", 800);
      IntStream.range(0, 3).forEach(i -> customers.get(i).addProduct(newProduct2));

      List<Product> ret = cs.mostPopularProduct();

      Assertions.assertThat(ret).isNotNull().hasSize(1).first().isEqualTo(newProduct);
   }

   @Test
   public void testCountBuys() {
      List<Customer> customers = DataProducer.getTestData(10, CustomerSampleFactory.getSample(1, 2, 5), CustomerSampleFactory.getSample(1, 4, 50), CustomerSampleFactory.getSample(1, 1, 13));
      CustomerServiceInterface cs = new CustomerService(customers);

      Product newProduct = new Product(9001, "Over 9000!!!", 9000.1);
      IntStream.range(0, 5).forEach(i -> customers.get(i).addProduct(newProduct));
      IntStream.range(0, 3).forEach(i -> customers.get(i).addProduct(newProduct));

      int ret = cs.countBuys(newProduct);

      Assertions.assertThat(ret).isEqualTo(8);
   }

   @Test
   public void testCountCustomersWhoBought() {
      List<Customer> customers = DataProducer.getTestData(10, CustomerSampleFactory.getSample(1, 2, 5), CustomerSampleFactory.getSample(1, 4, 50), CustomerSampleFactory.getSample(1, 1, 13));
      CustomerServiceInterface cs = new CustomerService(customers);

      Product newProduct = new Product(9001, "Over 9000!!!", 9000.1);
      IntStream.range(0, 5).forEach(i -> customers.get(i).addProduct(newProduct));
      IntStream.range(0, 3).forEach(i -> customers.get(i).addProduct(newProduct));

      int ret = cs.countCustomersWhoBought(newProduct);

      Assertions.assertThat(ret).isEqualTo(5);
   }

}
