package data_produce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import tests.SampleCustomization;
import entities.Customer;
import entities.Product;

public class DataProducer {

   private Random rand;

   public DataProducer() {
      this.rand = new Random();
   }

   /**
    * generates {@code count} customers<br>
    * first customer has {@code count} products, second - {@code count}-1
    * products an so on<br>
    * each product's price = [customer's products count] * 0.1
    */
   public List<Customer> getTestData(int count) {
      List<Customer> result = new ArrayList<>();

      for (int i = 0; i < count; i++) {
         Customer c = new Customer(i, "Customer: " + i);
         for (int j = count - i; j > 3; j--) {
            c.addProduct(new Product(j, "Product: " + j, j * 0.1));
         }
         result.add(c);
      }
      return result;
   }

   /**
    * @see SampleCustomization
    */
   public List<Customer> getTestData(int count, SampleCustomization... customization) {
      return processSamples(getTestData(count), Arrays.asList(customization));
   }

   private List<Customer> processSamples(List<Customer> testData, List<SampleCustomization> customization) {
      Map<Integer, Customer> map = new HashMap<>();
      Set<Customer> ret = new HashSet<>(testData.size());

      // perform customization
      customization.stream().forEach(s -> {
         Customer cust = map.get(s.getId());
         cust = s.build(cust != null ? cust : drawOne(testData));
         ret.add(cust);
         map.put(s.getId(), cust);
      });

      // copy the rest of the data
      ret.addAll(testData);
      return Arrays.asList(ret.toArray(new Customer[]{}));
   }

   /**
    * draws one random element from {@code l}
    */
   private <T> T drawOne(List<T> l) {
      return l.remove(rand.nextInt(l.size()));
   }

}
