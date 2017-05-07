package data_produce;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import entities.Customer;

public enum DataProducer {
   ;

   /**
    * generates {@code count} customers with no products
    */
   public static List<Customer> getTestData(int count) {
      return IntStream.rangeClosed(1, count).mapToObj((i) -> new Customer(i, "Customer" + i)).collect(Collectors.toList());
   }

   /**
    * @see PropertyCustomization
    */
   public static List<Customer> getTestData(int count, ICustomization... customization) {
      return processSamples(getTestData(count), Arrays.asList(customization));
   }

   private static List<Customer> processSamples(List<Customer> testData, List<ICustomization> list) {
      Map<Integer, Customer> map = new HashMap<>();
      Set<Customer> ret = new HashSet<>(testData.size());

      // perform customization on chosen objects
      list.stream().forEach(s -> {
         Customer cust = map.get(s.getId());
         cust = s.build(cust != null ? cust : GeneratingUtils.drawOne(testData));
         ret.add(cust);
         map.put(s.getId(), cust);
      });

      // copy the rest of the data
      ret.addAll(testData);
      return Arrays.asList(ret.toArray(new Customer[]{}));
   }

}
