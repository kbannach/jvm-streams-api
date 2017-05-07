package services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import entities.Customer;
import entities.Product;

public class CustomerService implements CustomerServiceInterface {

   private List<Customer> customers;

   public CustomerService(List<Customer> customers) {
      this.customers = customers;
   }

   @Override
   public List<Customer> findByName(String name) {
      return this.customers.stream().filter(c -> name.equals(c.getName())).collect(Collectors.toList());
   }

   @Override
   public List<Customer> findByField(String fieldName, Object value) {
      Field f;
      try {
         f = Customer.class.getDeclaredField(fieldName);
      } catch (NoSuchFieldException e) {
         return Collections.emptyList();
      } catch (SecurityException e) {
         throw new RuntimeException(e);
      }
      return this.customers.stream().filter(c -> Utils.compareNullSafe(Utils.getFieldValue(f, c), value)).collect(Collectors.toList());
   }

   @Override
   public List<Customer> customersWhoBoughtMoreThan(int number) {
      return this.customers.stream().filter(c -> c.getBoughtProducts().size() > number).collect(Collectors.toList());
   }

   @Override
   public List<Customer> customersWhoSpentMoreThan(double price) {
      return this.customers.stream().filter(c -> c.getBoughtProducts().stream().mapToDouble(Product::getPrice).sum() > price).collect(Collectors.toList());
   }

   @Override
   public List<Customer> customersWithNoOrders() {
      return this.customers.stream().filter(c -> c.getBoughtProducts().isEmpty()).collect(Collectors.toList());
   }

   @Override
   public void addProductToAllCustomers(Product p) {
      this.customers.stream().forEach(c -> c.addProduct(p));
   }

   @Override
   public double avgOrders(boolean includeEmpty) {
      return this.customers.stream().filter(c -> (includeEmpty ? true : !c.getBoughtProducts().isEmpty())).flatMap(c -> c.getBoughtProducts().stream()).mapToDouble(Product::getPrice).sum()
            / this.customers.stream().filter(c -> (includeEmpty ? true : !c.getBoughtProducts().isEmpty())).count();
   }

   @Override
   public boolean wasProductBought(Product p) {
      return this.customers.stream().anyMatch(c -> c.getBoughtProducts().contains(p));
   }

   @Override
   public List<Product> mostPopularProduct() {
      Map<Product, Integer> map = new HashMap<>();
      // counts every products' occurence number and stores it in a map
      this.customers.stream().flatMap(c -> c.getBoughtProducts().stream()).forEach(p -> map.compute(p, (k, v) -> v + 1));

      // gets a max number of occurences
      int max = map.values().stream().max(Integer::compareTo).get();

      // constructs array of most popular products
      List<Product> ret = new ArrayList<>();
      map.forEach((k, v) -> {
         if (v == max)
            ret.add(k);
      });
      return ret;
   }

   @Override
   public int countBuys(Product p) {
      return this.customers.stream().mapToInt(c -> (int) c.getBoughtProducts().stream().filter(bp -> bp.equals(p)).count()).sum();
   }

   @Override
   public int countCustomersWhoBought(Product p) {
      return (int) this.customers.stream().filter(c -> c.getBoughtProducts().contains(p)).count();
   }

}
